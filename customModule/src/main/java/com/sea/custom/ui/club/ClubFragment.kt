package com.sea.custom.ui.club

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
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.banner.BannerContact
import com.sea.custom.presenter.banner.BannerItem
import com.sea.custom.presenter.banner.BannerPresenter
import com.sea.custom.presenter.banner.NBannerModelReq
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.adapter.ShopBannerAdapter
import com.sea.custom.ui.club.about.AboutClubActivity
import com.sea.custom.ui.club.activity.ClubActivityActivity
import com.sea.custom.ui.club.match.ClubMatchActivity
import com.sea.custom.ui.member.MemberCustomActivity
import com.sea.custom.ui.result.ClubSearchResultActivity
import com.sea.custom.utils.DeviceUtils
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseFragment
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_club_layout.*
import kotlinx.android.synthetic.main.include_search_layout.*

class ClubFragment : BaseFragment(), ChannelContact.IChannelView, BannerContact.IBannerView {

    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@ClubFragment
            )
        }
    }

    private val bannerPresenter by lazy { BannerPresenter().apply { attachView(this@ClubFragment) } }

    private lateinit var mRecommendActivityAdapter: RecommendActivityAdapter

    private val mRecommendList = mutableListOf<NChannelItem>()

    private val nChannelModelReq = NChannelModelReq(
        channel_name = ChannelEnum.activity.name,
        is_red = 1
    )

    /*首页banner*/
    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_club_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "海钓俱乐部", false)
    }


    private fun initView() {
        mRecommendActivityAdapter = RecommendActivityAdapter(mRecommendList)
        if (DeviceUtils.isTabletDevice()) {
            rvRecommendAction.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvRecommendAction.layoutManager = LinearLayoutManager(context)
        }
        rvRecommendAction.adapter = mRecommendActivityAdapter
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
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setIndicator(CircleIndicator(context)).start()
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
        bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.club.name))
    }

    private fun initListener() {
        swipeLayout.setOnRefreshListener {
            bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.club.name))
            nChannelModelReq.page_index = 1
            nChannelPresenter.loadChannel(
                nChannelModelReq
            )
        }
        tvClub.setOnClickListener {
            startActivity(Intent(context, AboutClubActivity::class.java))
        }
        tvClubAction.setOnClickListener {
            startActivity(Intent(context, ClubActivityActivity::class.java))
        }
        tvClubMatch.setOnClickListener {
            startActivity(Intent(context, ClubMatchActivity::class.java))
        }
        tvMember.setOnClickListener {
            startActivity(Intent(context, MemberCustomActivity::class.java))
        }
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mRecommendList.clear()
        mRecommendList.addAll(mList)
        mRecommendActivityAdapter.notifyDataSetChanged()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchMallActivity.search_content_request_code && resultCode == SearchMallActivity.search_content_result_code) {
            val searchContent = data?.getStringExtra(SearchMallActivity.search_content_key) ?: ""
            startActivity(Intent(context, ClubSearchResultActivity::class.java).apply {
                putExtras(
                    ClubSearchResultActivity.getInstance(searchContent)
                )
            })
        }
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

    companion object {
        fun getInstance() = ClubFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}