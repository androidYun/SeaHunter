package com.sea.custom.ui.delicacy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
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
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.adapter.ShopBannerAdapter
import com.sea.custom.ui.delicacy.adapter.DelicacyKindAdapter
import com.sea.custom.ui.delicacy.adapter.DelicacyTypeAdapter
import com.sea.custom.ui.delicacy.introduce.DelicacyIntroduceActivity
import com.sea.custom.ui.delicacy.report.CheckReportActivity
import com.sea.custom.ui.delicacy.store.StoreDelicacyActivity
import com.sea.custom.ui.delicacy.vr.StoreVrActivity
import com.sea.custom.ui.result.XsDelicacyResultActivity
import com.sea.custom.utils.DeviceUtils
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_delicacy_layout.*
import kotlinx.android.synthetic.main.fragment_delicacy_layout.bannerView
import kotlinx.android.synthetic.main.fragment_delicacy_layout.swipeLayout
import kotlinx.android.synthetic.main.include_search_layout.*

class DelicacyFragment : BaseFragment(), ChannelContact.IChannelView, BannerContact.IBannerView,
    CategoryContact.ICategoryView {

    private val mChannelPresenter by lazy { ChannelPresenter().apply { attachView(this@DelicacyFragment) } }

    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@DelicacyFragment) } }

    private val bannerPresenter by lazy { BannerPresenter().apply { attachView(this@DelicacyFragment) } }

    private val nChannelModelReq = NChannelModelReq(
        channel_name = ChannelEnum.dish.name
    )

    private lateinit var mDelicacyKindAdapter: DelicacyKindAdapter


    private lateinit var mDelicacyTypeAdapter: DelicacyTypeAdapter


    private lateinit var mToDayActivityAdapter: ToDayActivityAdapter

    private val delicacyKindList = mutableListOf<NCategoryItem>()
    private val delicacyTypeList = mutableListOf<NDelicacyTypeItem>()
    private val toDayActivityList = mutableListOf<NChannelItem>()
    /*首页banner*/
    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "西沙美食之旅", false)
    }

    private fun initView() {
        /*种类*/
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rvDelicacyKind.layoutManager = layoutManager
        mDelicacyKindAdapter = DelicacyKindAdapter(delicacyKindList)
        rvDelicacyKind.adapter = mDelicacyKindAdapter

        /*类型*/
        delicacyTypeList.clear()
        delicacyTypeList.add(NDelicacyTypeItem("店内美食", "全部美食", R.mipmap.nav_food))
        delicacyTypeList.add(NDelicacyTypeItem("门店VR", "立体展示", R.mipmap.nav_vr))
        delicacyTypeList.add(NDelicacyTypeItem("检测报告", "权威检测", R.mipmap.nav_report))
        delicacyTypeList.add(NDelicacyTypeItem("渔获介绍", "介绍详细", R.mipmap.nav_introduce))
        if (DeviceUtils.isTabletDevice()) {
            rvDelicacyType.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            rvDelicacyType.layoutManager =
                GridLayoutManager(context, 2)
        }

        mDelicacyTypeAdapter = DelicacyTypeAdapter(delicacyTypeList)
        rvDelicacyType.adapter = mDelicacyTypeAdapter
        /*今日活动*/
        mToDayActivityAdapter = ToDayActivityAdapter(toDayActivityList)
        if (DeviceUtils.isTabletDevice()) {
            rvTodayOptimization.layoutManager =
                GridLayoutManager(context, 4)
        } else {
            rvTodayOptimization.layoutManager =
                GridLayoutManager(context, 2)
        }
        rvTodayOptimization.adapter = mToDayActivityAdapter

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
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.dish.name))
        mChannelPresenter.loadChannel(nChannelModelReq)
        bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.dish.name))
    }

    private fun initListener() {
        mDelicacyTypeAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(context, StoreDelicacyActivity::class.java))
                }
                1 -> {
                    startActivity(Intent(context, StoreVrActivity::class.java))
                }
                2 -> {
                    startActivity(Intent(context, CheckReportActivity::class.java))
                }
                3 -> {
                    startActivity(Intent(context, DelicacyIntroduceActivity::class.java))
                }
            }

        }
        swipeLayout.setOnRefreshListener {
            mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
            mChannelPresenter.loadChannel(nChannelModelReq)
            bannerPresenter.loadBanner()
        }
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        delicacyKindList.clear()
        delicacyKindList.addAll(mCategoryList)
        mDelicacyKindAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun loadCategoryFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        toDayActivityList.clear()
        toDayActivityList.addAll(mList)
        mToDayActivityAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
    }

    override fun loadBannerSuccess(data: List<BannerItem>) {
        mBannerList.clear()
        mBannerList.addAll(data.map { it.img_url })
        /*banner*/
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.adapter = shopBannerAdapter
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
            startActivity(Intent(context, XsDelicacyResultActivity::class.java).apply {
                putExtras(
                    XsDelicacyResultActivity.getInstance(searchContent)
                )
            })
        }
    }
    companion object {
        fun getInstance() = DelicacyFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}