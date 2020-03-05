package com.sea.user.activity.address.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class AddressListAdapter(mList: List<AddressListItem>) :
    BaseQuickAdapter<AddressListItem, BaseViewHolder>(R.layout.item_address_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: AddressListItem?) {

    }
}