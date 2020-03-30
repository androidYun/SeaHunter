package com.sea.user.activity.mall.order.list

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.parcel.Parcelize

class NShopOrderListModelReq(
    val command: Int = 15,
    var page_size: Int = 20,
    var page_index: Int = 1,
    var order_type: Int = 1,//1：商品订单 2：换购订单）
    var order_status: Int = 1//（0：全部 1：未支付 2：待发货 3：待收货 4：已完成）
)

data class NShopOrderListModelResponse(
    val code: Int = 0,
    val command: Int = 0,
    val `data`: List<ShopOrderListItem> = listOf(),
    val msg: String = "",
    val totalCount: Int = 0
)

@Parcelize
data class ShopOrderListItem(
    val accept_name: String? = "",
    val add_time: String? = "",
    val address: String? = "",
    val area: String? = "",
    val complete_time: String? = "",
    val confirm_time: String? = "",
    val email: String ?= "",
    val express_fee: Double = 0.0,
    val express_id: Int = 0,
    val express_no: String? = "",
    val express_status: Int = 0,
    val express_time: String? = "",
    val id: Int = 0,
    val invoice_taxes: Double = 0.0,
    val invoice_title: String? = "",
    val is_invoice: Int = 0,
    val message: String? = "",
    val mobile: String? = "",
    val order_amount: Double = 0.0,
    val order_goods: List<OrderGood>? = listOf(),
    val order_no: String? = "",
    val payable_amount: Double = 0.0,
    val payment_fee: Double = 0.0,
    val payment_id: Int = 0,
    val payment_status: Int = 0,
    val payment_time: String? = "",
    val point: Int = 0,
    val post_code: String ?= "",
    val real_amount: Double = 0.0,
    val remark: String = "",
    val shop_id: Int = 0,
    val site_id: Int = 0,
    val status: Int = 0,
    val telphone: String ?= "",
    val trade_no: String? = "",
    val user_id: Int = 0,
    val user_name: String ?= ""
) : Parcelable, MultiItemEntity {
    override fun getItemType(): Int {
        return status
    }
}


@Parcelize
data class OrderGood(
    val article_id: Int = 0,
    val channel_id: Int = 0,
    val goods_id: Int = 0,
    val goods_no: String? = "",
    val goods_price: Double = 0.0,
    val goods_title: String? = "",
    val id: Int = 0,
    val img_url: String? = "",
    val order_id: Int = 0,
    val point: Int = 0,
    val quantity: Int = 0,
    val real_price: Double = 0.0,
    val spec_text: String? = ""
) : Parcelable
