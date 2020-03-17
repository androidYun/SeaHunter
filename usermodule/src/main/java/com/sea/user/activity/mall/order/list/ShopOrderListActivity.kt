package com.sea.user.activity.mall.order.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_layout_view_pager.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 11/03/2020.
 * description:
 */
class ShopOrderListActivity : BaseSeaUserActivity() {


    private val tabTitle = mutableListOf(
        ShopOrderModel("全部", "1"),
        ShopOrderModel("待付款", "1"),
        ShopOrderModel("待发货", "1"),
        ShopOrderModel("待收货", "1"),
        ShopOrderModel("已完成", "1")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_order_list)
        initView()
    }

    private fun initView() {
        selectTab("订单")
        viewPage.adapter = ShopOrderPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPage)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    inner class ShopOrderPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                ShopOrderListFragment.getInstance(1)
            } else {
                ShopOrderListFragment.getInstance(1)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitle[position].titleName
        }

        override fun getCount(): Int {
            return tabTitle.size
        }
    }

    class ShopOrderModel(val titleName: String, val type: String)
}