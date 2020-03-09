package com.sea.user.activity.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.user.R
import com.sea.user.activity.feedback.complaint.ComplaintFeedbackFragment
import com.sea.user.activity.feedback.list.FeedBackListFragment
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_layout_view_pager.*

class FeedBackActivity : BaseActivity() {

    private val feedBackList = mutableListOf("投诉反馈", "我的反馈")

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

    inner class FeedBackPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 1) {
                ComplaintFeedbackFragment.getInstance()
            } else {
                FeedBackListFragment.getInstance()
            }
        }

        override fun getCount(): Int {
            return feedBackList.size
        }
    }
}