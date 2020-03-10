package com.sea.user.activity.mall.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_search_store.*

class SearchStoreActivity : BaseActivity(), SearchStoreContact.ISearchStoreView {

    private val mSearchStorePresenter by lazy { SearchStorePresenter().apply { attachView(this@SearchStoreActivity) } }

    private val nSearchStoreReq = NSearchStoreModelReq()

    private val mSearchStoreList = mutableListOf<SearchStoreItem>()

    private lateinit var mSearchStoreAdapter: SearchStoreAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_store)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mSearchStoreAdapter = SearchStoreAdapter(mSearchStoreList)
        rvSearchStore.layoutManager = LinearLayoutManager(this)
        rvSearchStore.adapter = mSearchStoreAdapter
    }

    private fun initData() {
        mSearchStorePresenter.loadSearchStore(nSearchStoreReq)
    }

    private fun initListener() {
        swipeSearchStore.setOnRefreshListener {
            mSearchStorePresenter.loadSearchStore(nSearchStoreReq)
        }
        mSearchStoreAdapter.setOnLoadMoreListener({
            if (nSearchStoreReq.pageIndex * nSearchStoreReq.pageSize < totalCount) {
                mSearchStorePresenter.loadSearchStore(nSearchStoreReq)
            } else {
                mSearchStoreAdapter.loadMoreEnd()
            }
        }, rvSearchStore)
    }

    override fun loadSearchStoreSuccess(mList: List<SearchStoreItem>, totalCount: Int) {
        if (nSearchStoreReq.pageIndex == 1) {
            mSearchStoreList.clear()
        }
        this.totalCount = totalCount
        mSearchStoreList.addAll(mList)
        mSearchStoreAdapter.notifyDataSetChanged()
        mSearchStoreAdapter.loadMoreComplete()
        swipeSearchStore.isRefreshing = false
        nSearchStoreReq.pageIndex++

    }

    override fun loadSearchStoreFail(throwable: Throwable) {
        handleError(throwable)
        swipeSearchStore.isRefreshing
        mSearchStoreAdapter.loadMoreComplete()
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