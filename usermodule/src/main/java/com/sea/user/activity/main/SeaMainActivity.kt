package com.sea.user.activity.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sea.user.R
import com.sea.user.activity.center.UserCenterFragment
import com.sea.user.activity.mall.SeaFoodMallFragment
import com.sea.user.activity.mall.car.ShopCarFragment
import com.sea.user.activity.mall.order.list.ShopOrderFragment
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sea_phone_mian.*

class SeaMainActivity : BaseActivity() {

    private lateinit var seaMailViewPageAdapter: SeaMailViewPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sea_phone_mian)
        initView()
        initListener()
    }

    private fun initListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                rgpView.check(position)
            }
        })
        rgpView.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rBtnSea -> {
                    viewPager.currentItem = 0
                }
                R.id.rBtnCar -> {
                    viewPager.currentItem = 1
                }
                R.id.rBtnOrder -> {
                    viewPager.currentItem = 2
                }
                R.id.rBtnMine -> {
                    viewPager.currentItem = 3
                }
            }

        }
    }

    private fun initView() {
        seaMailViewPageAdapter = SeaMailViewPageAdapter(supportFragmentManager)
        viewPager.adapter = seaMailViewPageAdapter
    }


    inner class SeaMailViewPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return SeaFoodMallFragment.getInstance()
                }
                1 -> {
                    return ShopCarFragment.getInstance()
                }
                2 -> {
                    return ShopOrderFragment.getInstance()
                }
                3 -> {
                    return UserCenterFragment.getInstance()
                }
                else -> {
                    return ShopCarFragment.getInstance()
                }
            }

        }

        override fun getCount(): Int {
            return 4
        }
    }
}