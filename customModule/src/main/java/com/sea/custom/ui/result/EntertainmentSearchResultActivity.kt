package com.sea.custom.ui.result

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.entertainment.list.EntertainmentListAdapter
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_result.*

class EntertainmentSearchResultActivity : BaseActivity(), ChannelContact.IChannelView {
    private val nChannelModelReq = NChannelModelReq(
        channel_name = ChannelEnum.arder.name
    )
    private lateinit var mEntertainmentListAdapter: EntertainmentListAdapter

    private val searchContent by lazy { intent.extras?.getString(search_result_key) ?: "" }

    private val mChannelList = mutableListOf<NChannelItem>()
    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@EntertainmentSearchResultActivity
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
        mEntertainmentListAdapter = EntertainmentListAdapter(mChannelList)
        mEntertainmentListAdapter.emptyView =
            emptyView
        rvSearchResult.layoutManager =
            LinearLayoutManager(this)
        rvSearchResult.adapter = mEntertainmentListAdapter
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mChannelList.clear()
        mChannelList.addAll(mList)
        mEntertainmentListAdapter.notifyDataSetChanged()
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