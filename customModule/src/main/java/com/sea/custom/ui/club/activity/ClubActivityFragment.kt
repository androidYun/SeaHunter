package com.sea.custom.ui.club.activity

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
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_activity_club.*

class ClubActivityFragment : BaseFragment(), ChannelContact.IChannelView {

    private val mClubActivityPresenter by lazy { ChannelPresenter().apply { attachView(this@ClubActivityFragment) } }

    private val nChannelModelReq = NChannelModelReq()

    private val mClubActivityList = mutableListOf<NChannelItem>()
    private val categoryId by lazy { arguments?.getInt(channel_key_id) ?: 0 }

    private lateinit var mClubActivityAdapter: ClubActivityAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_activity_club, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mClubActivityAdapter = ClubActivityAdapter(mClubActivityList)
        if (DeviceUtils.isTabletDevice()) {
            rvClubActivity.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvClubActivity.layoutManager = LinearLayoutManager(context)
        }
        rvClubActivity.adapter = mClubActivityAdapter
    }

    private fun initData() {
        nChannelModelReq.category_id = categoryId
        nChannelModelReq.channel_name = ChannelEnum.activity.name
        mClubActivityPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeClubActivity.setOnRefreshListener {
            mClubActivityPresenter.loadChannel(nChannelModelReq)
        }
        mClubActivityAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mClubActivityPresenter.loadChannel(nChannelModelReq)
            } else {
                mClubActivityAdapter.loadMoreEnd()
            }
        }, rvClubActivity)
    }


    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mClubActivityList.clear()
        }
        this.totalCount = totalCount
        mClubActivityList.addAll(mList)
        mClubActivityAdapter.notifyDataSetChanged()
        mClubActivityAdapter.loadMoreComplete()
        swipeClubActivity.isRefreshing = false
        nChannelModelReq.page_index++

    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeClubActivity.isRefreshing = false
        mClubActivityAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val channel_key_id = "channel_key_id"

        fun getInstance(categoryId: Int = 0) = ClubActivityFragment().apply {
            arguments = Bundle().apply {
                putInt(channel_key_id, categoryId)
            }
        }
    }
}