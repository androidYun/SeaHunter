package com.sea.user.activity.mall.order.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.common.Constants
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class OrderGoodAdapter(mList: List<OrderGood>) :
    BaseQuickAdapter<OrderGood, BaseViewHolder>(R.layout.item_order_good_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: OrderGood) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivOrderHead),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.setText(R.id.tvShopName, item.goods_title)
        helper?.setText(R.id.tvTags, item.spec_text)
        helper?.setText(R.id.tvCount, item.quantity.toString())
        helper?.setText(R.id.tvShopPrice, "￥${item.goods_price}")
    }
}