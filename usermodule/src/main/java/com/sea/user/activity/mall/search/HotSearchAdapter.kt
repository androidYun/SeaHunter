package com.sea.user.activity.mall.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class HotSearchAdapter(mList: List<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_store_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: String?) {

    }
}