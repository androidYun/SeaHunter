package com.sea.user.activity.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_layout_view_pager.*

class FeedBackActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_layout)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        viewPage.adapter = FeedBackPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPage)
    }

    private fun initData() {

    }

    private fun initListener() {


    }

    class FeedBackPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return Fragment()
        }

        override fun getCount(): Int {
            return 2
        }
    }
}