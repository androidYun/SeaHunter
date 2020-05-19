package com.sea.custom.ui.club.activity

import android.content.Intent
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
import com.sea.custom.ui.result.ClubActivityResultActivity
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_search_layout.*
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
        initListener()
    }


    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.activity.name))
    }

    private fun initView() {
        mClubActivityPageAdapter = ClubActivityPageAdapter(supportFragmentManager)
        viewPager.adapter = mClubActivityPageAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    private fun initListener() {
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(this, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchMallActivity.search_content_request_code && resultCode == SearchMallActivity.search_content_result_code) {
            val searchContent = data?.getStringExtra(SearchMallActivity.search_content_key) ?: ""
            startActivity(Intent(this, ClubActivityResultActivity::class.java).apply {
                putExtras(
                    ClubActivityResultActivity.getInstance(searchContent)
                )
            })
        }
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