package com.sea.custom.ui.club.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.sea.custom.R
import com.sea.custom.ui.adapter.web.WebViewItem
import com.sea.custom.ui.adapter.web.WebViewPageAdapter
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class ClubActivityActivity : BaseActivity() {

    private val mClubList= mutableListOf<WebViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_activity)
        initView()
    }

    private fun initView() {
        mClubList.add(WebViewItem("西沙游", ""))
        mClubList.add(WebViewItem("海岛游", ""))
        mClubList.add(WebViewItem("境外游", ""))
        viewPager.adapter = WebViewPageAdapter(mClubList,supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }
}