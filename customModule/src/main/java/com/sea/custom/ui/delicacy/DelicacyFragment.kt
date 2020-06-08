package com.sea.custom.ui.delicacy

import android.app.Activity
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
import com.sea.custom.ui.delicacy.vr.StoreVr2Activity
import com.sea.custom.ui.result.XsDelicacyResultActivity
import com.sea.custom.ui.vr.VrDetailActivity
import com.sea.custom.utils.DeviceUtils
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.UIUtils
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_delicacy_layout.*
import kotlinx.android.synthetic.main.fragment_delicacy_layout.bannerView
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

    private val REQUEST_CODE_SCAN = 100

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
        if (DeviceUtils.isTabletDevice()) {
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.CENTER
            rvDelicacyKind.layoutManager = layoutManager
        } else {
            rvDelicacyKind.layoutManager = GridLayoutManager(context, 4)
        }

        mDelicacyKindAdapter = DelicacyKindAdapter(delicacyKindList)
        rvDelicacyKind.adapter = mDelicacyKindAdapter

        bannerView.setIndicatorNormalColor(UIUtils.getInstance().getColor(R.color.color_9))
        bannerView.setIndicatorSelectedColor(UIUtils.getInstance().getColor(R.color.custom_theme_color))
        bannerView.setIndicatorGravity(IndicatorConfig.Direction.CENTER).indicator =
            CircleIndicator(context)

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
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setIndicator(CircleIndicator(context)).start()
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
                    startActivity(Intent(context, StoreVr2Activity::class.java))
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
            mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.dish.name))
            mChannelPresenter.loadChannel(nChannelModelReq)
            bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.dish.name))
        }
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
        ivQrScan.setOnClickListener {
            val intent = Intent(context, CaptureActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
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
            startActivity(Intent(context, XsDelicacyResultActivity::class.java).apply {
                putExtras(
                    XsDelicacyResultActivity.getInstance(searchContent)
                )
            })
        } else if (requestCode == REQUEST_CODE_SCAN) {
            if (null != data) {
                val bundle = data.extras ?: return
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val content = data?.getStringExtra(CodeUtils.RESULT_STRING)
                    if (content.isNullOrBlank() || !content.contains("hnzhiling.com", false)) {
                        return
                    }
                    startActivity(Intent(context, VrDetailActivity::class.java).apply {
                        putExtras(
                            VrDetailActivity.getInstance(content)
                        )
                    })
                }

            }
        }
    }

    companion object {
        fun getInstance() = DelicacyFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}