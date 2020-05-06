package com.sea.custom.ui.entertainment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import kotlinx.android.synthetic.main.fragment_entertainment_list.*
import com.xhs.baselibrary.base.BaseFragment

class EntertainmentListFragment : BaseFragment(), ChannelContact.IChannelView {

    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@EntertainmentListFragment
            )
        }
    }

    private val nChannelModelReq = NChannelModelReq()

    private val categoryId by lazy { arguments?.getInt(channel_key_id) ?: 0 }

    private val mChannelList = mutableListOf<NChannelItem>()

    private lateinit var mEntertainmentListAdapter: EntertainmentListAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_entertainment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mEntertainmentListAdapter = EntertainmentListAdapter(mChannelList)
        rvEntertainmentList.layoutManager = LinearLayoutManager(context)
        rvEntertainmentList.adapter = mEntertainmentListAdapter
    }

    private fun initData() {
        nChannelModelReq.category_id = categoryId
        nChannelModelReq.channel_name=ChannelEnum.arder.name
        nChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeEntertainmentList.setOnRefreshListener {
            nChannelPresenter.loadChannel(nChannelModelReq)
        }
        mEntertainmentListAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                nChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mEntertainmentListAdapter.loadMoreEnd()
            }
        }, rvEntertainmentList)
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mChannelList.clear()
        }
        this.totalCount = totalCount
        mChannelList.addAll(mList)
        mEntertainmentListAdapter.notifyDataSetChanged()
        mEntertainmentListAdapter.loadMoreComplete()
        swipeEntertainmentList.isRefreshing = false
        nChannelModelReq.page_index++
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeEntertainmentList.isRefreshing = false
        mEntertainmentListAdapter.loadMoreComplete()
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {

        private const val channel_key_id = "channel_key_id"

        fun getInstance(categoryId: Int = 0) = EntertainmentListFragment().apply {
            arguments = Bundle().apply {
                putInt(channel_key_id, categoryId)
            }
        }
    }
}