package com.sea.user.activity.mall.order.detail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import com.sea.user.activity.mall.order.list.OrderGoodAdapter
import com.sea.user.activity.mall.order.list.ShopOrderListItem
import com.sea.user.constant.OrderEnum
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mall_order_detail.*

class MallOrderDetailActivity : BaseActivity() {
    private val shopOrderListItem by lazy {
        intent?.extras?.getParcelable(mall_shop_order_detail_item_key) ?: ShopOrderListItem()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mall_order_detail)
        initData()
        initListener()
    }


    private fun initData() {
        tvOrderTopStatus.text =
            OrderEnum.getOrder(shopOrderListItem.status, shopOrderListItem.payment_status)
                .statusTitle
        tvNamePhoneNumber.text = shopOrderListItem.accept_name.plus("  ${shopOrderListItem.mobile}")
        tvDetailAddress.text =
            shopOrderListItem.area + shopOrderListItem.address
        rvMallConfirmOrder.layoutManager = LinearLayoutManager(this)
        rvMallConfirmOrder?.adapter = OrderGoodAdapter(shopOrderListItem.order_goods)
        tvOrderNo.text = "订单号:${shopOrderListItem.order_no}"
        tvOrderTime.text = "下单时间:${shopOrderListItem.add_time}"
        tvOrderStatus.text = "订单状态:${OrderEnum.getOrder(
            shopOrderListItem.status,
            shopOrderListItem.payment_status
        ).statusTitle}"
        if (shopOrderListItem.status == OrderEnum.WaitPayment.status) {
            ivOrderStatus.setBackgroundResource(R.mipmap.ic_details_payment)
        } else if (shopOrderListItem.status == OrderEnum.WaitPayment.status) {
            if (shopOrderListItem.payment_status == 1) {
                ivOrderStatus.setBackgroundResource(R.mipmap.ic_details_delivery)
            } else {
                ivOrderStatus.setBackgroundResource(R.mipmap.ic_details_goods)
            }
        } else if (shopOrderListItem.status == OrderEnum.AlreadyFinish.status) {
            ivOrderStatus.setBackgroundResource(R.mipmap.ic_details_complete)
        }
    }

    private fun initListener() {

    }

    companion object {
        private const val mall_shop_order_detail_item_key = "mall_shop_order_detail_item_key"
        fun getInstance(
            shopOrderListItem: ShopOrderListItem
        ): Bundle {
            return Bundle().apply {
                putParcelable(mall_shop_order_detail_item_key, shopOrderListItem)
            }
        }
    }
}