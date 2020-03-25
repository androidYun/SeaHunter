package com.sea.user.activity.address.list

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class AddressListAdapter(mList: List<AddressListItem>) :
    BaseQuickAdapter<AddressListItem, BaseViewHolder>(R.layout.item_address_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: AddressListItem) {
        helper?.getView<TextView>(R.id.tvEditAddress)?.setOnClickListener {
          onItemClickListener.onItemClick(this,it,helper.layoutPosition)
        }
        helper?.getView<ConstraintLayout>(R.id.cvSelectAddress)?.setOnClickListener {
          onItemClickListener.onItemClick(this,it,helper.layoutPosition)
        }
        helper?.setText(R.id.tvNamePhoneNumber, "${item.accept_name}  ${item.mobile}")
        helper?.setText(
            R.id.tvDetailAddress,
            "${item.province}${item.city}${item.area}${item.address}"
        )
    }
}