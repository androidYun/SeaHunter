package com.sea.user.activity.mall.order.result

import android.os.Bundle
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.UIUtils
import kotlinx.android.synthetic.main.activity_order_result.*

class OrderResultActivity : BaseActivity() {

    private val orderResult by lazy { intent.extras?.getBoolean(order_result_key) ?: false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_result)
        initView()
        initListener()
    }


    private fun initView() {
        title = if (orderResult) "下单成功" else "下单失败"
        ivOrderResultStatus.setBackgroundResource(if (orderResult) R.mipmap.ic_successful else R.mipmap.ic_failure)
        tvOrderResultStatus.setTextColor(
            if (orderResult) UIUtils.getInstance().getColor(R.color.user_theme_color) else UIUtils.getInstance().getColor(
                R.color.color_c1220d
            )
        )
        tvOrderResultStatus.text =
            if (orderResult) "订单支付成功!" else "订单支付失败！"

        cvBackMain.setCardBackgroundColor(
            if (orderResult) UIUtils.getInstance().getColor(R.color.user_theme_color) else UIUtils.getInstance().getColor(
                R.color.color_c1220d
            )
        )
        cvResetPay.setCardBackgroundColor(
            if (orderResult) UIUtils.getInstance().getColor(R.color.user_theme_color) else UIUtils.getInstance().getColor(
                R.color.color_c1220d
            )
        )
        tvOrderOperator.text = if (orderResult) "查看订单" else "重新支付"

    }

    private fun initListener() {
        tvOrderOperator.setOnClickListener {

        }
        tvBackMain.setOnClickListener {

        }
    }

    companion object {
        const val order_result_key = "order_result_key"
        fun getInstance() = Bundle().apply { }
    }
}