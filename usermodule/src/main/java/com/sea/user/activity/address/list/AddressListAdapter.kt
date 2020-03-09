package com.sea.user.activity.address.list

import android.content.Intent
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.address.AddAddressActivity

class AddressListAdapter(mList: List<AddressListItem>) :
    BaseQuickAdapter<AddressListItem, BaseViewHolder>(R.layout.item_address_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: AddressListItem?) {
        helper?.getView<TextView>(R.id.tvEditAddress)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, AddAddressActivity::class.java))
        }
    }
}