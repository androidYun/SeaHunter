package com.sea.custom.ui.delicacy.report

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.publicmodule.activity.web.WebActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class CheckReportAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(R.layout.item_check_report_layout, mList) {
    override fun convert(helper: BaseViewHolder, item: NChannelItem) {
        helper?.setText(R.id.tvName, item.title)
        ImageLoader.loadImageWithUrl(
            helper.getView(R.id.ivFish),
            Constants.baseUrl.plus(item.img_url)
        )
         helper?.getView<LinearLayout>(R.id.lvShopDetail)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, WebActivity::class.java).apply {
                putExtras(
                    WebActivity.getInstance(item?.content ?: "", item?.title ?: "")
                )
            })
        }
    }
}