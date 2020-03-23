package com.sea.user.activity.integral.exchange

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_exchange_list.*

class ExchangeListActivity : BaseActivity(), ExchangeListContact.IExchangeListView {

    private val mExchangeListPresenter by lazy { ExchangeListPresenter().apply { attachView(this@ExchangeListActivity) } }

    private val nExchangeListReq = NExchangeListModelReq()

    private val mExchangeListList = mutableListOf<ExchangeListItem>()

    private lateinit var mExchangeListAdapter: ExchangeListAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_list)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mExchangeListAdapter = ExchangeListAdapter(mExchangeListList)
        rvExchangeList.layoutManager = LinearLayoutManager(this)
        rvExchangeList.adapter = mExchangeListAdapter
    }

    private fun initData() {
        mExchangeListPresenter.loadExchangeList(nExchangeListReq)
    }

    private fun initListener() {
        swipeExchangeList.setOnRefreshListener {
            mExchangeListPresenter.loadExchangeList(nExchangeListReq)
        }
        mExchangeListAdapter.setOnLoadMoreListener({
            if (nExchangeListReq.page_index * nExchangeListReq.page_size < totalCount) {
                mExchangeListPresenter.loadExchangeList(nExchangeListReq)
            } else {
                mExchangeListAdapter.loadMoreEnd()
            }
        }, rvExchangeList)
    }

    override fun loadExchangeListSuccess(mList: List<ExchangeListItem>, totalCount: Int) {
        if (nExchangeListReq.page_index == 1) {
            mExchangeListList.clear()
        }
        this.totalCount = totalCount
        mExchangeListList.addAll(mList)
        mExchangeListAdapter.notifyDataSetChanged()
        mExchangeListAdapter.loadMoreComplete()
        swipeExchangeList.isRefreshing = false
        nExchangeListReq.page_index++

    }

    override fun loadExchangeListFail(throwable: Throwable) {
        handleError(throwable)
        swipeExchangeList.isRefreshing
        mExchangeListAdapter.loadMoreComplete()
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