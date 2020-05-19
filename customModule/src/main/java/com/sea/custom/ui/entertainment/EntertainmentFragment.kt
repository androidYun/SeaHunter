package com.sea.custom.ui.entertainment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.sea.custom.ui.entertainment.list.EntertainmentListFragment
import com.sea.custom.ui.result.EntertainmentSearchResultActivity
import com.sea.publicmodule.activity.search.SearchMallActivity
import kotlinx.android.synthetic.main.fragment_entertainment_layout.*
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.include_search_layout.*
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class EntertainmentFragment : BaseFragment(), CategoryContact.ICategoryView {


    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@EntertainmentFragment) } }

    private val mEntertainmentList = mutableListOf<NCategoryItem>()

    private lateinit var mEntertainmentPagerAdapter: FragmentPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_entertainment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "娱乐视频", false)
    }

    private fun initView() {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mEntertainmentPagerAdapter = EntertainmentPageAdapter(childFragmentManager)
        viewPager.adapter = mEntertainmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    /**
     * Fragment中初始化Toolbar
     * @param toolbar
     * @param title 标题
     * @param isDisplayHomeAsUp 是否显示返回箭头
     */
    private fun initToolbar(toolbar: Toolbar?, title: String?, isDisplayHomeAsUp: Boolean) {
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        val actionBar: ActionBar? = appCompatActivity.supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp)
        }
    }

    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.arder.name))
    }

    private fun initListener() {
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
        swipeLayout.setOnRefreshListener {
            mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.arder.name))
        }
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        mEntertainmentList.clear()
        mEntertainmentList.addAll(mCategoryList)
        mEntertainmentPagerAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
        swipeLayout.isEnabled = false
    }

    override fun loadCategoryFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
        swipeLayout.isEnabled = true
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
            startActivity(Intent(context, EntertainmentSearchResultActivity::class.java).apply {
                putExtras(
                    EntertainmentSearchResultActivity.getInstance(searchContent)
                )
            })
        }
    }
    companion object {
        fun getInstance() = EntertainmentFragment().apply {
            arguments = Bundle().apply { }
        }
    }

    inner class EntertainmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return EntertainmentListFragment.getInstance(mEntertainmentList[position].id)
        }

        override fun getCount(): Int {
            return mEntertainmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mEntertainmentList[position].title
        }
    }
}