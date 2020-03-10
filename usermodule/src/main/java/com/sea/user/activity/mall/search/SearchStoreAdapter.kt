package com.sea.user.activity.mall.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class SearchStoreAdapter(mList: List<SearchStoreItem>) :
    BaseQuickAdapter<SearchStoreItem, BaseViewHolder>(R.layout.item_search_store_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: SearchStoreItem?) {

    }
}