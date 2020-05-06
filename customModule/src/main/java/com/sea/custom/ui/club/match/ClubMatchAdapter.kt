package com.sea.custom.ui.club.match

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.channel.NChannelItem
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
    }
}