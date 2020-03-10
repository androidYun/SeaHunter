package com.sea.user.activity.mall.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class MallListAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_mall_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallListItem?) {

    }
}