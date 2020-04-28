package com.sea.custom.ui.delicacy.report

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import kotlinx.android.synthetic.main.activity_check_report.*

class CheckReportActivity : BaseActivity(), CheckReportContact.ICheckReportView {

    private val mCheckReportPresenter by lazy { CheckReportPresenter().apply { attachView(this@CheckReportActivity) } }

    private val nCheckReportReq = NCheckReportModelReq()

    private val mCheckReportList = mutableListOf<CheckReportItem>()

    private lateinit var mCheckReportAdapter: CheckReportAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_report)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mCheckReportAdapter = CheckReportAdapter(mCheckReportList)
        rvCheckReport.layoutManager = LinearLayoutManager(this)
        rvCheckReport.adapter = mCheckReportAdapter
    }

    private fun initData() {
        mCheckReportPresenter.loadCheckReport(nCheckReportReq)
    }

    private fun initListener() {
        swipeCheckReport.setOnRefreshListener {
            mCheckReportPresenter.loadCheckReport(nCheckReportReq)
        }
        mCheckReportAdapter.setOnLoadMoreListener({
            if (nCheckReportReq.page_index * nCheckReportReq.page_size < totalCount) {
                mCheckReportPresenter.loadCheckReport(nCheckReportReq)
            } else {
                mCheckReportAdapter.loadMoreEnd()
            }
        }, rvCheckReport)
        mCheckReportAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {

                }
                1 -> {

                }
                2 -> {
                    startActivity(Intent(this, CheckReportActivity::class.java))
                }
                3 -> {

                }
            }

        }
    }

    override fun loadCheckReportSuccess(mList: List<CheckReportItem>, totalCount: Int) {
        if (nCheckReportReq.page_index == 1) {
            mCheckReportList.clear()
        }
        this.totalCount = totalCount
        mCheckReportList.addAll(mList)
        mCheckReportAdapter.notifyDataSetChanged()
        mCheckReportAdapter.loadMoreComplete()
        swipeCheckReport.isRefreshing = false
        nCheckReportReq.page_index++

    }

    override fun loadCheckReportFail(throwable: Throwable) {
        handleError(throwable)
        swipeCheckReport.isRefreshing
        mCheckReportAdapter.loadMoreComplete()
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