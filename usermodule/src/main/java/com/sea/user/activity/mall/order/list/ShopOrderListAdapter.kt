package com.sea.user.activity.mall.order.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class ShopOrderListAdapter(mList: List<ShopOrderListItem>) :
    BaseMultiItemQuickAdapter<ShopOrderListItem, BaseViewHolder>(mList) {
    init {
        addItemType(1, R.layout.item_wait_pay_shop_order)
        addItemType(2, R.layout.item_ship_shop_order)
        addItemType(3, R.layout.item_wait_received_shop_order)
        addItemType(4, R.layout.item_finish_shop_order)
    }


    override fun convert(helper: BaseViewHolder, item: ShopOrderListItem) {
        when (getItemViewType(helper.layoutPosition)) {
            1 -> {
                helper.setText(R.id.tvShopOrderState, "待付款")
                helper.addOnClickListener(R.id.tvCancelOrder)
                helper.addOnClickListener(R.id.tvLookDetail)
                helper.addOnClickListener(R.id.tvOncePay)
            }
            2 -> {
                helper.setText(R.id.tvShopOrderState, "待发货")
                helper.addOnClickListener(R.id.tvLookDetail)
                helper.addOnClickListener(R.id.tvLookLogistics)
            }
            3 -> {
                helper.setText(R.id.tvShopOrderState, "待收货")
                helper.addOnClickListener(R.id.tvLookLogistics)
                helper.addOnClickListener(R.id.tvConfirmReceipt)
            }
            4 -> {
                helper.setText(R.id.tvShopOrderState, "已完成")
            }
        }
        helper.setText(R.id.tvShopOrderNo,"订单号：${item.order_no}")
        helper.setText(R.id.tvShopOrderState, "代付款")
        val rvOrderShop = helper.getView<RecyclerView>(R.id.rvOrderShop)
        rvOrderShop.layoutManager = LinearLayoutManager(mContext)
        rvOrderShop.adapter = OrderGoodAdapter(item.order_goods)
    }
}