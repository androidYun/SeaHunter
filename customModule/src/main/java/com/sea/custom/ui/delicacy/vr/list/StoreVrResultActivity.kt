package com.sea.custom.ui.delicacy.vr.list

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.club.match.ClubMatchAdapter
import com.sea.custom.utils.DeviceUtils
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_result.*

class StoreVrResultActivity : BaseActivity(), ChannelContact.IChannelView {
    private val nChannelModelReq = NChannelModelReq(
        channel_name = ChannelEnum.shop.name
    )
    private val mStoreVrListList = mutableListOf<NChannelItem>()

    private lateinit var mStoreVrListAdapter: StoreVrListAdapter

    private val searchContent by lazy { intent.extras?.getString(search_result_key) ?: "" }
    
    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@StoreVrResultActivity
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        initView()
        initData()
    }

    private fun initData() {
        nChannelModelReq.key = searchContent
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
    }

    private fun initView() {
        swipeSearchResult.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            nChannelPresenter.loadChannel(
                nChannelModelReq
            )
        }
        mStoreVrListAdapter = StoreVrListAdapter(mStoreVrListList)
        mStoreVrListAdapter.emptyView =
            emptyView
        if (DeviceUtils.isTabletDevice()) {
            rvSearchResult.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvSearchResult.layoutManager = LinearLayoutManager(this)
        }
        rvSearchResult.adapter = mStoreVrListAdapter
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mStoreVrListList.clear()
        mStoreVrListList.addAll(mList)
        mStoreVrListAdapter.notifyDataSetChanged()
        swipeSearchResult.isRefreshing = false
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeSearchResult.isRefreshing = false
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val search_result_key = "search_result_key"
        fun getInstance(searchContent: String = "") = Bundle().apply {
            putString(search_result_key, searchContent)

        }
    }
}