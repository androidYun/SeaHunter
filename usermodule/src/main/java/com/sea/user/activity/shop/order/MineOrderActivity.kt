package com.sea.user.activity.shop.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mine_wallet.*

class MineOrderActivity : BaseActivity() {

    private val tabTitle = mutableListOf(
        ShopModel("待付款", "1"),
        ShopModel("代发货", "1"),
        ShopModel("待收货", "1"),
        ShopModel("已完成", "1")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_order)
        initView()
    }

    private fun initView() {
        viewPage.adapter = ShopOrderPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPage)
        tabLayout.tabMode = TabLayout.MODE_FIXED;
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL;
    }

    inner class ShopOrderPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                ShopOrderFragment.getInstance()
            } else {
                ShopOrderFragment.getInstance()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitle[position].titleName
        }

        override fun getCount(): Int {
            return tabTitle.size
        }
    }

    class ShopModel(val titleName: String, val type: String)
}