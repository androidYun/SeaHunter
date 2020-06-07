package com.sea.custom.ui.delicacy.store

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.delicacy.ToDayActivityAdapter
import com.sea.custom.ui.delicacy.introduce.DelicacyIntroduceAdapter
import com.sea.custom.ui.entertainment.list.EntertainmentListFragment
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_store_delicacy.*

class StoreDelicacyActivity : BaseActivity(), ChannelContact.IChannelView {
    private val categoryId by lazy {
        intent?.extras?.getInt(channel_key_id) ?: null
    }

    private val mChannelPresenter by lazy { ChannelPresenter().apply { attachView(this@StoreDelicacyActivity) } }


    private val nChannelModelReq = NChannelModelReq()

    private val mStoreDelicacyList = mutableListOf<NChannelItem>()

    private lateinit var mToDayActivityAdapter: ToDayActivityAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_delicacy)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mToDayActivityAdapter = ToDayActivityAdapter(mStoreDelicacyList)
        mToDayActivityAdapter.emptyView =
            emptyView
        if (DeviceUtils.isTabletDevice()) {
            rvStoreDelicacy.layoutManager =
                GridLayoutManager(this, 4)
        } else {
            rvStoreDelicacy.layoutManager =
                GridLayoutManager(this, 2)
        }
        rvStoreDelicacy.adapter = mToDayActivityAdapter
    }

    private fun initData() {
        nChannelModelReq.channel_name = ChannelEnum.dish.name
        nChannelModelReq.category_id = categoryId
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeStoreDelicacy.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mToDayActivityAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mToDayActivityAdapter.loadMoreEnd()
            }
        }, rvStoreDelicacy)
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mStoreDelicacyList.clear()
        }
        this.totalCount = totalCount
        mStoreDelicacyList.addAll(mList)
        mToDayActivityAdapter.notifyDataSetChanged()
        mToDayActivityAdapter.loadMoreComplete()
        swipeStoreDelicacy.isRefreshing = false
        nChannelModelReq.page_index++

    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeStoreDelicacy.isRefreshing
        mToDayActivityAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val channel_key_id = "channel_key_id"
        fun getInstance(categoryId: Int) = Bundle().apply {
            putInt(channel_key_id, categoryId)
        }
    }
}