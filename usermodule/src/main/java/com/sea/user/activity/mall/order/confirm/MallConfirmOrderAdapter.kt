package com.sea.user.activity.mall.order.confirm

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.car.ConfirmOrderShopItem
import com.sea.user.common.Constants
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.xhs.baselibrary.weight.AmountView

class MallConfirmOrderAdapter(mList: List<ConfirmOrderShopItem>) :
    BaseQuickAdapter<ConfirmOrderShopItem, BaseViewHolder>(
        R.layout.item_mall_confirm_order_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: ConfirmOrderShopItem) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivOrderHead),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.getView<AmountView>(R.id.AmountNumber)?.setGoods_storage(item.stock_quantity)
        helper?.getView<AmountView>(R.id.AmountNumber)?.setGoodsCount(item.quantity)
        helper?.setText(R.id.tvShopName, item.title)
        helper?.setText(R.id.tvTags, item.spec_text)
        helper?.setText(R.id.tvShopPrice, "￥${item.sell_price}")
    }
}