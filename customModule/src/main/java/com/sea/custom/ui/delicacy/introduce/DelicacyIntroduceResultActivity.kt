package com.sea.custom.ui.delicacy.introduce

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

class DelicacyIntroduceResultActivity : BaseActivity(), ChannelContact.IChannelView {
    private val nChannelModelReq = NChannelModelReq(
    )
    private lateinit var mDelicacyIntroduceAdapter: DelicacyIntroduceAdapter

    private val searchContent by lazy { intent.extras?.getString(search_result_key) ?: "" }
    private val channelName by lazy { intent.extras?.getString(channel_name_key) ?: "" }

    private val mDelicacyIntroduceList = mutableListOf<NChannelItem>()
    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@DelicacyIntroduceResultActivity
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
        nChannelModelReq.channel_name = channelName
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
        mDelicacyIntroduceAdapter = DelicacyIntroduceAdapter(mDelicacyIntroduceList)
        mDelicacyIntroduceAdapter.emptyView =
            emptyView
        rvSearchResult.layoutManager = LinearLayoutManager(this)
        rvSearchResult.adapter = mDelicacyIntroduceAdapter
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mDelicacyIntroduceList.clear()
        mDelicacyIntroduceList.addAll(mList)
        mDelicacyIntroduceAdapter.notifyDataSetChanged()
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
        private const val channel_name_key = "channel_name_key"
        fun getInstance(searchContent: String = "", channelName: String = "") = Bundle().apply {
            putString(search_result_key, searchContent)
            putString(channel_name_key, channelName)

        }
    }
}