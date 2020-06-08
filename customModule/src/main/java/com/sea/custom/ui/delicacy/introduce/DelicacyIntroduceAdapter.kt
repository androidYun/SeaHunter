package com.sea.custom.ui.delicacy.introduce

import android.content.Intent
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.ui.delicacy.introduce.detail.DelicacyIntroduceDetailActivity
import com.sea.custom.web.ChannelWebDetailActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class DelicacyIntroduceAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(
        R.layout.item_delicacy_introduce_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem) {
        helper?.setText(R.id.tvFishName, item?.title ?: "")
        helper?.setText(R.id.tvIntroduce, item?.zhaiyao ?: "")
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivFish),
            Constants.baseUrl.plus(item?.img_url ?: "")
        )
        helper?.getView<ConstraintLayout>(R.id.lvShopDetail)?.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    DelicacyIntroduceDetailActivity::class.java
                ).apply {
                    putExtras(
                        DelicacyIntroduceDetailActivity.getInstance(
                            item
                        )
                    )
                })
        }
    }
}