package com.sea.custom.ui.member

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
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_member_custom.*

class MemberCustomActivity : BaseActivity(), ChannelContact.IChannelView {

    private val mChannelPresenter by lazy { ChannelPresenter().apply { attachView(this@MemberCustomActivity) } }

    private val mNChannelModelReq = NChannelModelReq()

    private val mMemberCustomList = mutableListOf<NChannelItem>()

    private lateinit var mMemberCustomAdapter: MemberCustomAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_custom)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMemberCustomAdapter = MemberCustomAdapter(mMemberCustomList)
        if (DeviceUtils.isTabletDevice()) {
            rvMemberCustom.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvMemberCustom.layoutManager = LinearLayoutManager(this)
        }
        rvMemberCustom.adapter = mMemberCustomAdapter
    }

    private fun initData() {
        mNChannelModelReq.channel_name = ChannelEnum.service.name
        mChannelPresenter.loadChannel(mNChannelModelReq)
    }

    private fun initListener() {
        swipeMemberCustom.setOnRefreshListener {
            mChannelPresenter.loadChannel(mNChannelModelReq)
        }
        mMemberCustomAdapter.setOnLoadMoreListener({
            if (mNChannelModelReq.page_index * mNChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(mNChannelModelReq)
            } else {
                mMemberCustomAdapter.loadMoreEnd()
            }
        }, rvMemberCustom)
    }


    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (mNChannelModelReq.page_index == 1) {
            mMemberCustomList.clear()
        }
        this.totalCount = totalCount
        mMemberCustomList.addAll(mList)
        mMemberCustomAdapter.notifyDataSetChanged()
        mMemberCustomAdapter.loadMoreComplete()
        swipeMemberCustom.isRefreshing = false
        mNChannelModelReq.page_index++
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeMemberCustom.isRefreshing
        mMemberCustomAdapter.loadMoreComplete()
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}