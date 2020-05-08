package com.sea.custom.ui.delicacy.vr.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_activity_store_vr_list.*
import com.xhs.baselibrary.base.BaseFragment

class StoreVrListFragment : BaseFragment(), ChannelContact.IChannelView {

    private val mChannelPresenter by lazy { ChannelPresenter().apply { attachView(this@StoreVrListFragment) } }

    private val nChannelModelReq = NChannelModelReq()

    private val mStoreVrListList = mutableListOf<NChannelItem>()

    private lateinit var mStoreVrListAdapter: StoreVrListAdapter

    private var totalCount = 0

    private val sort by lazy { arguments?.getInt(sort_vr_key) ?: 0 }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_activity_store_vr_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mStoreVrListAdapter = StoreVrListAdapter(mStoreVrListList)
        if (DeviceUtils.isTabletDevice()) {
            rvStoreVrList.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvStoreVrList.layoutManager = LinearLayoutManager(context)
        }
        rvStoreVrList.adapter = mStoreVrListAdapter
    }

    private fun initData() {
        nChannelModelReq.channel_name = ChannelEnum.shop.name
        nChannelModelReq.sort = sort
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeStoreVrList.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mStoreVrListAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mStoreVrListAdapter.loadMoreEnd()
            }
        }, rvStoreVrList)
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mStoreVrListList.clear()
        }
        this.totalCount = totalCount
        mStoreVrListList.addAll(mList)
        mStoreVrListAdapter.notifyDataSetChanged()
        mStoreVrListAdapter.loadMoreComplete()
        swipeStoreVrList.isRefreshing = false
        nChannelModelReq.page_index++
    }


    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeStoreVrList.isRefreshing = false
        mStoreVrListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val sort_vr_key = "sort_vr_key"
        fun getInstance(sort: Int = 0) = StoreVrListFragment().apply {
            arguments = Bundle().apply {
                putInt(sort_vr_key, sort)
            }
        }
    }
}