package com.sea.user.activity.mall.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.presenter.store.NStoreListItemModel
import com.sea.user.presenter.store.NStoreListModelReq
import com.sea.user.presenter.store.StoreListContact
import com.sea.user.presenter.store.StoreListPresenter
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_search_store.*

class SearchStoreActivity : BaseActivity(), SearchStoreContact.ISearchStoreView,
    StoreListContact.IStoreListView {

    private val mSearchStorePresenter by lazy { SearchStorePresenter().apply { attachView(this@SearchStoreActivity) } }


    private val nSearchStoreReq = NSearchStoreModelReq()

    private val mSearchStoreList = mutableListOf<SearchStoreItem>()

    private val mHotSearchStoreList = mutableListOf<String>()

    private lateinit var mSearchStoreAdapter: SearchStoreAdapter

    private lateinit var mHotSearchAdapter: HotSearchAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_store)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mHotSearchAdapter = HotSearchAdapter(mHotSearchStoreList)
        rvHotSearch.layoutManager = LinearLayoutManager(this)
        rvHotSearch.adapter = mHotSearchAdapter
        mSearchStoreAdapter = SearchStoreAdapter(mSearchStoreList)
        rvSearchStore.layoutManager = LinearLayoutManager(this)
        rvSearchStore.adapter = mSearchStoreAdapter
    }

    private fun initData() {
        mSearchStorePresenter.loadHotSearch()
        // mSearchStorePresenter.loadSearchStore(nSearchStoreReq)
    }

    private fun initListener() {
        evSearchHot.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchContent = evSearchHot.text.toString()
                    if (searchContent.isNullOrBlank()) {
                        ToastUtils.show("请输入搜索内容")
                        return false
                    }

                    return true;
                }
                return false;
            }
        })
        swipeSearchStore.setOnRefreshListener {
            //mSearchStorePresenter.loadSearchStore(nSearchStoreReq)
        }
        mSearchStoreAdapter.setOnLoadMoreListener({
            if (nSearchStoreReq.pageIndex * nSearchStoreReq.pageSize < totalCount) {
                //mSearchStorePresenter.loadSearchStore(nSearchStoreReq)
            } else {
                mSearchStoreAdapter.loadMoreEnd()
            }
        }, rvSearchStore)
    }


    override fun loadHotSearchSuccess(mList: List<String>) {
        if (nSearchStoreReq.pageIndex == 1) {
            mSearchStoreList.clear()
        }
        this.totalCount = totalCount
        // mSearchStoreList.addAll(mList)
        mSearchStoreAdapter.notifyDataSetChanged()
        mSearchStoreAdapter.loadMoreComplete()
        swipeSearchStore.isRefreshing = false
        nSearchStoreReq.pageIndex++

    }

    override fun clearSearch() {

    }

    override fun loadStoreListSuccess(mList: List<NStoreListItemModel>) {

    }

    override fun loadAddSearch() {

    }

    override fun loadStoreListFail(throwable: Throwable) {
        handleError(throwable)
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
        const val search_store_name_result_key = 101
        fun getInstance() = Bundle().apply { }
    }
}