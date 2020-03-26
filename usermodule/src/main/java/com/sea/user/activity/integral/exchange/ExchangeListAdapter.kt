package com.sea.user.activity.integral.exchange

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.order.list.ShopOrderListItem
import com.sea.user.common.Constants
import com.xhs.baselibrary.utils.UIUtils
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class ExchangeListAdapter(mList: List<ShopOrderListItem>) :
    BaseQuickAdapter<ShopOrderListItem, BaseViewHolder>(R.layout.item_exchange_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopOrderListItem) {
        if (!item.order_goods.isNullOrEmpty()) {
            ImageLoader.loadImageWithUrl(
                helper?.getView(R.id.ivShopImage),
                Constants.baseUrl.plus(item.order_goods[0].img_url)
            )
            helper?.setText(R.id.tvShopName, item.order_goods[0].goods_title)
            helper?.setText(R.id.tvShopIntegral, item.order_goods[0].point.toString())
        }
        helper?.setText(R.id.tvExchangeTime, item.add_time)
        if (item.express_status == 1) {
            helper?.setText(
                R.id.tvIntegralStatus,
                "待发货"
            )
            helper?.setTextColor( R.id.tvIntegralStatus,UIUtils.getInstance().getColor(R.color.user_theme_color))
        } else if (item.express_status == 2) {
            helper?.setText(
                R.id.tvIntegralStatus,
                "已发货  单号:${item.express_no}"
            )
            helper?.setTextColor( R.id.tvIntegralStatus,UIUtils.getInstance().getColor(R.color.color_129109))
        }
    }
}