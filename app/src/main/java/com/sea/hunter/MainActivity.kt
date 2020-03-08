package com.sea.hunter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sea.user.activity.center.UserCenterActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.shop.order.MineOrderActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, UserCenterActivity::class.java))
    }
}
