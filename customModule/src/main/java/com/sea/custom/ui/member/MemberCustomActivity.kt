package com.sea.custom.ui.member

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import kotlinx.android.synthetic.main.activity_member_custom.*

class MemberCustomActivity : BaseActivity(), MemberCustomContact.IMemberCustomView {

    private val mMemberCustomPresenter by lazy { MemberCustomPresenter().apply { attachView(this@MemberCustomActivity) } }

    private val nMemberCustomReq = NMemberCustomModelReq()

    private val mMemberCustomList = mutableListOf<MemberCustomItem>()

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
        rvMemberCustom.layoutManager = LinearLayoutManager(this)
        rvMemberCustom.adapter = mMemberCustomAdapter
    }

    private fun initData() {
        mMemberCustomPresenter.loadMemberCustom(nMemberCustomReq)
    }

    private fun initListener() {
        swipeMemberCustom.setOnRefreshListener {
            mMemberCustomPresenter.loadMemberCustom(nMemberCustomReq)
        }
        mMemberCustomAdapter.setOnLoadMoreListener({
            if (nMemberCustomReq.pageIndex * nMemberCustomReq.pageSize < totalCount) {
                mMemberCustomPresenter.loadMemberCustom(nMemberCustomReq)
            } else {
                mMemberCustomAdapter.loadMoreEnd()
            }
        }, rvMemberCustom)
    }

    override fun loadMemberCustomSuccess(mList: List<MemberCustomItem>, totalCount: Int) {
        if (nMemberCustomReq.pageIndex == 1) {
            mMemberCustomList.clear()
        }
        this.totalCount = totalCount
        mMemberCustomList.addAll(mList)
        mMemberCustomAdapter.notifyDataSetChanged()
        mMemberCustomAdapter.loadMoreComplete()
        swipeMemberCustom.isRefreshing = false
        nMemberCustomReq.pageIndex++

    }

    override fun loadMemberCustomFail(throwable: Throwable) {
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