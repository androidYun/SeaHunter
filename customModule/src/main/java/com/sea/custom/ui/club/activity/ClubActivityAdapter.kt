package com.sea.custom.ui.club.activity

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.web.ChannelWebDetailActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class ClubActivityAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(R.layout.item_activity_club_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem) {
        helper?.setText(R.id.tvActivityTitle, item.title)
        helper?.setText(R.id.tvActivityContent, item.zhaiyao)
        helper?.setText(R.id.tvActivityTitle, item.title)
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivActivityImage),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.getView<LinearLayout>(R.id.lvRecommendActivity)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, ChannelWebDetailActivity::class.java).apply {
                putExtras(
                    ChannelWebDetailActivity.getInstance(
                        item?.id ?: 0,
                        ChannelEnum.club.name,
                        item?.title ?: ""
                    )
                )
            })
        }
    }
}