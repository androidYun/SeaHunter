package com.sea.custom.ui.make

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
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.banner.BannerContact
import com.sea.custom.presenter.banner.BannerItem
import com.sea.custom.presenter.banner.BannerPresenter
import com.sea.custom.presenter.banner.NBannerModelReq
import com.sea.custom.presenter.category.CategoryContact
import com.sea.custom.presenter.category.CategoryPresenter
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.presenter.category.NCategoryModelReq
import com.sea.custom.ui.adapter.ShopBannerAdapter
import com.sea.custom.ui.make.list.DelicacyMakeListFragment
import com.sea.custom.ui.result.DelicacyMakeResultActivity
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.UIUtils
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_member_custom_detail.*
import kotlinx.android.synthetic.main.fragment_delicacy_make.*
import kotlinx.android.synthetic.main.fragment_delicacy_make.bannerView
import kotlinx.android.synthetic.main.fragment_delicacy_make.swipeLayout
import kotlinx.android.synthetic.main.include_search_layout.*
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class DelicacyMakeFragment : BaseFragment(),
    BannerContact.IBannerView,
    CategoryContact.ICategoryView {


    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@DelicacyMakeFragment) } }
    private val bannerPresenter by lazy { BannerPresenter().apply { attachView(this@DelicacyMakeFragment) } }
    private val mDelicacyMakeList = mutableListOf<NCategoryItem>()
    private lateinit var mEntertainmentPagerAdapter: FragmentPagerAdapter
    /*首页banner*/
    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_make, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "西沙美食制作", false)
    }

    private fun initView() {
        mEntertainmentPagerAdapter = EntertainmentPageAdapter(childFragmentManager)
        viewPager.adapter = mEntertainmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setIndicator(CircleIndicator(context)).start()
    }

    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
        bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.food.name))
    }

    private fun initListener() {
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
        swipeLayout.setOnRefreshListener {
            mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
            bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.food.name))
        }
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

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        mDelicacyMakeList.clear()
        mDelicacyMakeList.addAll(mCategoryList)
        mEntertainmentPagerAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun loadCategoryFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
    }


    override fun loadBannerSuccess(data: List<BannerItem>) {
        mBannerList.clear()
        mBannerList.addAll(data.map { it.img_url })
        /*banner*/
        shopBannerAdapter.notifyDataSetChanged()
    }

    override fun loadBannerFail(throwable: Throwable) {
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
            startActivity(Intent(context, DelicacyMakeResultActivity::class.java).apply {
                putExtras(
                    DelicacyMakeResultActivity.getInstance(searchContent)
                )
            })
        }
    }
    companion object {
        fun getInstance() = DelicacyMakeFragment().apply {
            arguments = Bundle().apply { }
        }
    }

    inner class EntertainmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return DelicacyMakeListFragment.getInstance(mDelicacyMakeList[position].id)
        }

        override fun getCount(): Int {
            return mDelicacyMakeList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mDelicacyMakeList[position].title
        }
    }
}