package com.sea.user.activity.integral.detail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_integral_detail.*

class IntegralDetailActivity : BaseActivity(), IntegralDetailContact.IIntegralDetailView {

    private val mIntegralDetailPresenter by lazy { IntegralDetailPresenter().apply { attachView(this@IntegralDetailActivity) } }

    private val nIntegralDetailReq = NIntegralDetailModelReq()

    private val mIntegralDetailList = mutableListOf<IntegralDetailItem>()

    private lateinit var mIntegralDetailAdapter: IntegralDetailAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mIntegralDetailAdapter = IntegralDetailAdapter(mIntegralDetailList)
        rvIntegralDetail.layoutManager = LinearLayoutManager(this)
        rvIntegralDetail.adapter = mIntegralDetailAdapter
    }

    private fun initData() {
        mIntegralDetailPresenter.loadIntegralDetail(nIntegralDetailReq)
    }

    private fun initListener() {
        swipeIntegralDetail.setOnRefreshListener {
            mIntegralDetailPresenter.loadIntegralDetail(nIntegralDetailReq)
        }
        mIntegralDetailAdapter.setOnLoadMoreListener({
            if (nIntegralDetailReq.page_index * nIntegralDetailReq.page_size < totalCount) {
                mIntegralDetailPresenter.loadIntegralDetail(nIntegralDetailReq)
            } else {
                mIntegralDetailAdapter.loadMoreEnd()
            }
        }, rvIntegralDetail)
    }

    override fun loadIntegralDetailSuccess(mList: List<IntegralDetailItem>, totalCount: Int) {
        if (nIntegralDetailReq.page_index == 1) {
            mIntegralDetailList.clear()
        }
        this.totalCount = totalCount
        mIntegralDetailList.addAll(mList)
        mIntegralDetailAdapter.notifyDataSetChanged()
        mIntegralDetailAdapter.loadMoreComplete()
        swipeIntegralDetail.isRefreshing = false
        nIntegralDetailReq.page_index++

    }

    override fun loadIntegralDetailFail(throwable: Throwable) {
        handleError(throwable)
        swipeIntegralDetail.isRefreshing
        mIntegralDetailAdapter.loadMoreComplete()
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