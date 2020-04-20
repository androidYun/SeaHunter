package com.sea.custom.ui.club.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.custom.R
import com.sea.custom.ui.adapter.web.WebViewItem
import com.sea.custom.ui.adapter.web.WebViewPageAdapter
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class AboutClubActivity : BaseActivity() {

    private val mClubList = mutableListOf<WebViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_club)
        initView()
    }

    private fun initView() {
        mClubList.add(WebViewItem("专业刺钓船", ""))
        mClubList.add(WebViewItem("专职钓手", ""))
        mClubList.add(WebViewItem("专业刺钓具", ""))
        viewPager.adapter = WebViewPageAdapter(mClubList, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }
}