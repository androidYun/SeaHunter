package com.sea.custom.ui.delicacy.introduce

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
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class DelicacyIntroduceActivity : BaseActivity(), CategoryContact.ICategoryView {

    private val mDelicacyIntroduceList = mutableListOf<NCategoryItem>()
    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@DelicacyIntroduceActivity) } }
    private lateinit var mEntertainmentPagerAdapter: FragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delicacy_introduce)
        initView()
        initData()
    }

    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.activity.name))
    }

    private fun initView() {
        mEntertainmentPagerAdapter = DelicacyIntroducePageAdapter( supportFragmentManager)
        viewPager.adapter = mEntertainmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        mDelicacyIntroduceList.clear()
        mDelicacyIntroduceList.addAll(mCategoryList)
        mEntertainmentPagerAdapter.notifyDataSetChanged()
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
    inner class DelicacyIntroducePageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return DelicacyIntroduceFragment.getInstance()
        }

        override fun getCount(): Int {
            return mDelicacyIntroduceList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mDelicacyIntroduceList[position].title
        }
    }
}