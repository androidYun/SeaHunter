package com.sea.custom.ui.club.activity

import android.os.Bundle
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

    private val mClubList = mutableListOf<WebViewItem>()
    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@ClubActivityActivity) } }

    private lateinit var webViewPageAdapter: WebViewPageAdapter
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
        webViewPageAdapter = WebViewPageAdapter(mClubList, supportFragmentManager)
        viewPager.adapter = webViewPageAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        val list = mCategoryList.map { WebViewItem(it.title, "") }
        mClubList.clear()
        mClubList.addAll(list)
        webViewPageAdapter.notifyDataSetChanged()
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
}