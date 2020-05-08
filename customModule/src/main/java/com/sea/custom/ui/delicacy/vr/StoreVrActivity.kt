package com.sea.custom.ui.delicacy.vr

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.custom.R
import com.sea.custom.ui.delicacy.vr.list.StoreVrListFragment
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_store_vr.*

class StoreVrActivity : BaseActivity() {

    private val mStoreVrList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_vr)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mStoreVrList.add("默认")
        mStoreVrList.add("离我最近")
        mStoreVrList.add("查看更多")
        viewPager.adapter = EntertainmentPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initListener() {

    }


    companion object {
        fun getInstance() = Bundle().apply { }
    }

    inner class EntertainmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    StoreVrListFragment.getInstance(0)
                }
                1 -> {
                    StoreVrListFragment.getInstance(4)
                }
                else -> {
                    StoreVrListFragment.getInstance(3)
                }
            }
        }

        override fun getCount(): Int {
            return mStoreVrList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mStoreVrList[position]
        }
    }
}