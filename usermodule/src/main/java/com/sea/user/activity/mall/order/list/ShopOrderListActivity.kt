package com.sea.user.activity.mall.order.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity
import com.sea.user.activity.center.UserCenterFragment
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_layout_view_pager.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 11/03/2020.
 * description:
 */
class ShopOrderListActivity : BaseSeaUserActivity() {

    private val status by lazy { intent.extras?.getInt(order_status_key) ?: 0 }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_order_list)
        initView()
    }

    private fun initView() {
        selectTab("订单")
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frameLayout, ShopOrderFragment.getInstance(status))
        beginTransaction.commit()
    }


    companion object {
        private const val order_status_key = "order_status_key"
        fun getInstance(status: Int = 0) = Bundle().apply {
            putInt(order_status_key, status)
        }
    }
}