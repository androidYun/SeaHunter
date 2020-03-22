package com.sea.user.activity.mall.car

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.weight.AmountView

class ShopCarAdapter(mList: List<ShopCarItem>) :
    BaseQuickAdapter<ShopCarItem, BaseViewHolder>(R.layout.item_shop_car_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopCarItem) {
        helper?.getView<AmountView>(R.id.AmountNumber)?.setGoods_storage(100)
        helper?.setText(R.id.tvShopName, item.title)
//        helper?.setText(R.id.tvShopName,item.tvTags)
//        helper?.setText(R.id.tvShopName,item.sell_price)
        helper?.getView<CheckBox>(R.id.cbSelect)?.isChecked = item.isCheck
        helper?.getView<CheckBox>(R.id.cbSelect)?.setOnClickListener {
            mData[helper.layoutPosition].isCheck = !mData[helper.layoutPosition].isCheck
            notifyItemChanged(helper.layoutPosition)
        }
    }

    fun isAllSelect(isChecked: Boolean = true) {
        mData.forEach { it.isCheck = isChecked }
        notifyDataSetChanged()
    }

    fun getSelectList(): List<Int> {
       return  mData.filter { it.isCheck }.map { it.goods_id }
    }
}