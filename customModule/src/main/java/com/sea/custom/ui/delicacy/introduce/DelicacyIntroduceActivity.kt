package com.sea.custom.ui.delicacy.introduce

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
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_search_layout.*
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
        initListener()
    }

    private fun initListener() {
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(this, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
    }

    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.fish.name))
    }

    private fun initView() {
        mEntertainmentPagerAdapter = DelicacyIntroducePageAdapter(supportFragmentManager)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchMallActivity.search_content_request_code && resultCode == SearchMallActivity.search_content_result_code) {
            val searchContent = data?.getStringExtra(SearchMallActivity.search_content_key) ?: ""
            startActivity(Intent(this, DelicacyIntroduceResultActivity::class.java).apply {
                putExtras(
                    DelicacyIntroduceResultActivity.getInstance(searchContent)
                )
            })
        }
    }

    inner class DelicacyIntroducePageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return DelicacyIntroduceFragment.getInstance(mDelicacyIntroduceList[position].id)
        }

        override fun getCount(): Int {
            return mDelicacyIntroduceList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mDelicacyIntroduceList[position].title
        }
    }
}