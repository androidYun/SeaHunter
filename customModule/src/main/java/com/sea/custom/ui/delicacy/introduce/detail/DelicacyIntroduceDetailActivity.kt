package com.sea.custom.ui.delicacy.introduce.detail

import android.os.Bundle
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.detail.ChannelDetailContact
import com.sea.custom.presenter.channel.detail.ChannelDetailPresenter
import com.sea.custom.presenter.channel.detail.NChannelDetailModelReq
import com.sea.custom.ui.adapter.ShopBannerAdapter
import com.sea.custom.ui.club.DetailWebFragment
import com.xhs.baselibrary.base.BaseActivity
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_delicacy_introduce_detail.*

class DelicacyIntroduceDetailActivity : BaseActivity(), ChannelDetailContact.IChannelDetailView {

    private val mChannelItem by lazy {
        intent?.extras?.getParcelable(Channel_Item_key) ?: NChannelItem()
    }

    private val mChannelDetailPresenter by lazy { ChannelDetailPresenter().apply { attachView(this@DelicacyIntroduceDetailActivity) } }

    private lateinit var shopBannerAdapter: ShopBannerAdapter
    private val mBannerList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delicacy_introduce_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setIndicator(CircleIndicator(this)).start()
    }

    private fun initData() {
        mChannelDetailPresenter.loadChannelDetail(
            NChannelDetailModelReq(
                channel_name = ChannelEnum.fish.name,
                id = mChannelItem.id ?: -1
            )
        )
        supportFragmentManager.beginTransaction()
            .add(
                R.id.frameLayout,
                DetailWebFragment.getInstance(mChannelItem.id ?: -1, ChannelEnum.fish.name)
            )
            .commit()
    }

    private fun initListener() {
        swipeMemberCustomDetail.setOnRefreshListener {
            mChannelDetailPresenter.loadChannelDetail(
                NChannelDetailModelReq(
                    channel_name = ChannelEnum.club.name,
                    id = mChannelItem.id ?: -1
                )
            )
        }
        ivBack.setOnClickListener { finish() }
    }


    override fun loadChannelDetailSuccess(content: NChannelItem) {
        tvName.text = content.title
        tvCategoryName.text = content.category_name
        tvPlace.text = content.place
        tvEatWay.text = content.eat_way
        mBannerList.clear()
        if (!content.albums.isNullOrEmpty()) {
            mBannerList.addAll(content.albums.map { it?.original_path ?: "" })
        } else if (!content.img_url.isNullOrBlank()) {
            mBannerList.add(content.img_url)
        }
        shopBannerAdapter.notifyDataSetChanged()
        swipeMemberCustomDetail.isRefreshing = false
    }

    override fun loadChannelDetailFail(throwable: Throwable) {

    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val Channel_Item_key = "Channel_Item_key"
        fun getInstance(
            nChannelItem: NChannelItem
        ) = Bundle().apply {
            putParcelable(Channel_Item_key, nChannelItem)
        }
    }
}