package com.sea.user.activity.mall

import android.os.Bundle
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity

class SeaFoodMallActivity : BaseSeaUserActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sea_food_mall)
        initView()
    }


    private fun initView() {
        selectTab("点餐")
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frameLayout, SeaFoodMallFragment.getInstance())
        beginTransaction.commit()
    }


}