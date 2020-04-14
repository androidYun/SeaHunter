package com.sea.custom.ui.club.match

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class ClubMatchAdapter(mList: List<ClubMatchItem>) :
    BaseQuickAdapter<ClubMatchItem, BaseViewHolder>(R.layout.item_club_match_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ClubMatchItem?) {

    }
}