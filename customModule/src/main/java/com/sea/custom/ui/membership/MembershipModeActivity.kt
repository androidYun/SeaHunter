package com.sea.custom.ui.membership

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import kotlinx.android.synthetic.main.activity_member_ship_mode.*

class MembershipModeActivity : BaseActivity(), ChannelContact.IChannelView {

    private val mChannelPresenter by lazy { ChannelPresenter().apply { attachView(this@MembershipModeActivity) } }

    private val nChannelModelReq = NChannelModelReq()

    private val mMembershipModeList = mutableListOf<NChannelItem>()

    private lateinit var mMembershipModeAdapter: MembershipModeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_ship_mode)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMembershipModeAdapter = MembershipModeAdapter(mMembershipModeList)
        rvMembershipMode.layoutManager = LinearLayoutManager(this)
        rvMembershipMode.adapter = mMembershipModeAdapter
    }

    private fun initData() {
        nChannelModelReq.channel_name=ChannelEnum.food.name
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeMembershipMode.setOnRefreshListener {
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mMembershipModeAdapter.setOnItemClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tvMemberShipMode -> {

                }
            }
        }
    }


    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mMembershipModeList.addAll(mList)
        mMembershipModeAdapter.notifyDataSetChanged()
        mMembershipModeAdapter.loadMoreComplete()
        swipeMembershipMode.isRefreshing = false
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeMembershipMode.isRefreshing
        mMembershipModeAdapter.loadMoreComplete()
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