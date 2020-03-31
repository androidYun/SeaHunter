package com.sea.user.activity.mall.car

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity

class ShopCarActivity : BaseSeaUserActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_car)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        selectTab("购物车")
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frameLayout, ShopCarFragment.getInstance())
        beginTransaction.commit()
    }

    private fun initData() {

    }

    private fun initListener() {

    }


    companion object {
        fun getInstance() = Bundle().apply { }
    }
}