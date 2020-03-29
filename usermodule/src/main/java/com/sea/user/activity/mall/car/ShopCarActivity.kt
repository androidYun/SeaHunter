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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                if (item.title == "编辑") {
                    item.title = "取消"
                } else {
                    item.title = "编辑"
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}