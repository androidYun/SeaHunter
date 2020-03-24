package com.sea.user.activity.mall.order.confirm

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.car.ShopCarItem
import com.sea.user.common.Constants
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class MallConfirmOrderAdapter(mList: List<ShopCarItem>) :
    BaseQuickAdapter<ShopCarItem, BaseViewHolder>(R.layout.item_mall_confirm_order_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopCarItem) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivOrderHead),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.setText(R.id.tvShopName, item.title)
        helper?.setText(R.id.tvTags, item.spec_text)
        helper?.setText(R.id.tvShopPrice, "￥${item.sell_price}")
    }
}