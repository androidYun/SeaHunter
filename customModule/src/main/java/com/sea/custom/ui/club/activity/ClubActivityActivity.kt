package com.sea.custom.ui.club.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.category.CategoryContact
import com.sea.custom.presenter.category.CategoryPresenter
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.presenter.category.NCategoryModelReq
import com.sea.custom.ui.adapter.web.WebViewItem
import com.sea.custom.ui.adapter.web.WebViewPageAdapter
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class ClubActivityActivity : BaseActivity(), CategoryContact.ICategoryView {

    private val mClubList = mutableListOf<NCategoryItem>()
    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@ClubActivityActivity) } }

    private lateinit var mClubActivityPageAdapter: ClubActivityPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_activity)
        initView()
        initData()
    }

    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.activity.name))
    }

    private fun initView() {
        mClubActivityPageAdapter = ClubActivityPageAdapter( supportFragmentManager)
        viewPager.adapter = mClubActivityPageAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        mClubList.clear()
        mClubList.addAll(mCategoryList)
        mClubActivityPageAdapter.notifyDataSetChanged()
    }

    override fun loadCategoryFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    inner class ClubActivityPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return ClubActivityFragment.getInstance(mClubList[position].id)
        }

        override fun getCount(): Int {
            return mClubList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mClubList[position].title
        }
    }
}