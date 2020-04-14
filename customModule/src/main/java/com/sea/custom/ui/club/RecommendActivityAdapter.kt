package com.sea.custom.ui.club

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R

class RecommendActivityAdapter(mList: List<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recommend_activity, mList) {

    override fun convert(helper: BaseViewHolder?, item: String?) {

    }
}