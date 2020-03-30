package com.sea.user.activity.mall.detail

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ShopParamsAdapter(mList: List<NShopParamsItem>) :
    BaseQuickAdapter<NShopParamsItem, BaseViewHolder>(R.layout.item_shop_params_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NShopParamsItem?) {
        helper?.setText(R.id.tvShopParamsTitle, "${item?.title}  ")
        helper?.setText(R.id.tvShopParamsValue, item?.value)

    }
}