package com.sea.hunter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sea.user.activity.center.UserCenterActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.mall.SeaFoodMallActivity
import com.sea.user.activity.mall.order.confirm.MallConfirmOrderActivity
import com.sea.user.activity.mall.order.list.ShopOrderListActivity
import com.sea.user.activity.mall.order.result.OrderResultActivity
import com.sea.user.activity.shop.order.MineOrderActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startActivity(Intent(this, OrderResultActivity::class.java))
        startActivity(Intent(this, UserCenterActivity::class.java))
    }
}
