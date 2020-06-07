package com.sea.custom.ui.club.match

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

class ClubMatchAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(R.layout.item_club_match_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem) {
        helper?.setText(R.id.tvMatchName, item.title)
        helper?.setText(R.id.tvMatchVs, item.sub_title)
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.tvMatchBanner),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.getView<LinearLayout>(R.id.lvMatch)?.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    ChannelWebDetailActivity::class.java
                ).apply {
                    putExtras(
                        ChannelWebDetailActivity.getInstance(
                            item?.id ?: 0,
                            ChannelEnum.game.name,
                            item?.title ?: ""
                        )
                    )
                })
        }
    }
}