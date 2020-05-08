package com.sea.custom.ui.club.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.adapter.web.WebViewItem
import com.sea.custom.ui.adapter.web.WebViewPageAdapter
import com.sea.custom.ui.club.DetailWebFragment
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class AboutClubActivity : BaseActivity(), ChannelContact.IChannelView {

    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@AboutClubActivity
            )
        }
    }

    private val mClubList = mutableListOf<NChannelItem>()

    private lateinit var aboutClubPageAdapter:AboutClubPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_club)
        initView()
        initData()
    }

    private fun initData() {
        nChannelPresenter.loadChannel(NChannelModelReq(channel_name = ChannelEnum.club.name))
    }

    private fun initView() {
         aboutClubPageAdapter= AboutClubPageAdapter(supportFragmentManager)
        viewPager.adapter=aboutClubPageAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mClubList.clear()
        mClubList.addAll(mList)
        aboutClubPageAdapter.notifyDataSetChanged()
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    inner class AboutClubPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return DetailWebFragment.getInstance(mClubList[position].id?:0,ChannelEnum.club.name)
        }

        override fun getCount(): Int {
            return mClubList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mClubList[position].title
        }
    }
}