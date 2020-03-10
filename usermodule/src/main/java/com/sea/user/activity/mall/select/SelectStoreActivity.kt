package com.sea.user.activity.mall.select

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_select_store.*

class SelectStoreActivity : BaseActivity(), SelectStoreContact.ISelectStoreView {

    private val mSelectStorePresenter by lazy { SelectStorePresenter().apply { attachView(this@SelectStoreActivity) } }

    private val nSelectStoreReq = NSelectStoreModelReq()

    private val mSelectStoreList = mutableListOf<SelectStoreItem>()

    private lateinit var mSelectStoreAdapter: SelectStoreAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_store)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mSelectStoreAdapter = SelectStoreAdapter(mSelectStoreList)
        rvSelectStore.layoutManager = LinearLayoutManager(this)
        rvSelectStore.adapter = mSelectStoreAdapter
    }

    private fun initData() {
        mSelectStorePresenter.loadSelectStore(nSelectStoreReq)
    }

    private fun initListener() {
        swipeSelectStore.setOnRefreshListener {
            mSelectStorePresenter.loadSelectStore(nSelectStoreReq)
        }
        mSelectStoreAdapter.setOnLoadMoreListener({
            if (nSelectStoreReq.pageIndex * nSelectStoreReq.pageSize < totalCount) {
                mSelectStorePresenter.loadSelectStore(nSelectStoreReq)
            } else {
                mSelectStoreAdapter.loadMoreEnd()
            }
        }, rvSelectStore)
    }

    override fun loadSelectStoreSuccess(mList: List<SelectStoreItem>, totalCount: Int) {
        if (nSelectStoreReq.pageIndex == 1) {
            mSelectStoreList.clear()
        }
        this.totalCount = totalCount
        mSelectStoreList.addAll(mList)
        mSelectStoreAdapter.notifyDataSetChanged()
        mSelectStoreAdapter.loadMoreComplete()
        swipeSelectStore.isRefreshing = false
        nSelectStoreReq.pageIndex++

    }

    override fun loadSelectStoreFail(throwable: Throwable) {
        handleError(throwable)
        swipeSelectStore.isRefreshing
        mSelectStoreAdapter.loadMoreComplete()
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