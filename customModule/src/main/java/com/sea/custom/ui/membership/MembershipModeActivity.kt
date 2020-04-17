package com.sea.custom.ui.membership

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import kotlinx.android.synthetic.main.activity_member_ship_mode.*

class MembershipModeActivity : BaseActivity(), MembershipModeContact.IMembershipModeView {

    private val mMembershipModePresenter by lazy { MembershipModePresenter().apply { attachView(this@MembershipModeActivity) } }

    private val nMembershipModeReq = NMembershipModeModelReq()

    private val mMembershipModeList = mutableListOf<MembershipModeItem>()

    private lateinit var mMembershipModeAdapter: MembershipModeAdapter

    private var totalCount = 0

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
        mMembershipModePresenter.loadMembershipMode(nMembershipModeReq)
    }

    private fun initListener() {
        swipeMembershipMode.setOnRefreshListener {
            mMembershipModePresenter.loadMembershipMode(nMembershipModeReq)
        }
        mMembershipModeAdapter.setOnLoadMoreListener({
            if (nMembershipModeReq.pageIndex * nMembershipModeReq.pageSize < totalCount) {
                mMembershipModePresenter.loadMembershipMode(nMembershipModeReq)
            } else {
                mMembershipModeAdapter.loadMoreEnd()
            }
        }, rvMembershipMode)
    }

    override fun loadMembershipModeSuccess(mList: List<MembershipModeItem>, totalCount: Int) {
        if (nMembershipModeReq.pageIndex == 1) {
            mMembershipModeList.clear()
        }
        this.totalCount = totalCount
        mMembershipModeList.addAll(mList)
        mMembershipModeAdapter.notifyDataSetChanged()
        mMembershipModeAdapter.loadMoreComplete()
        swipeMembershipMode.isRefreshing = false
        nMembershipModeReq.pageIndex++

    }

    override fun loadMembershipModeFail(throwable: Throwable) {
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