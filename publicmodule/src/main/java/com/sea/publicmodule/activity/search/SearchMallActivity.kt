package com.sea.publicmodule.activity.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.publicmodule.R
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_search_mall.*

class SearchMallActivity : BaseActivity(), SearchMallContact.ISearchMallView {
    private val mSearchStorePresenter by lazy { SearchMallPresenter().apply { attachView(this@SearchMallActivity) } }


    private lateinit var mSearchHotAdapter: SearchAdapter

    private val mHotList = mutableListOf<SearchItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_mall)
        initView()
        initData()
        initListener()
    }

    private fun initData() {
        mSearchHotAdapter = SearchAdapter(mHotList)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rvSearch.layoutManager = layoutManager
        rvSearch.adapter = mSearchHotAdapter
        mSearchStorePresenter.loadSearch()
    }

    private fun initView() {

    }

    var searchContent: String = ""
    private fun initListener() {
        evSearchHot.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContent = evSearchHot.text.toString()
                    if (searchContent.isNullOrBlank()) {
                        ToastUtils.show("请输入搜索内容")
                        return false
                    }
                    mSearchStorePresenter.loadAddSearch(NAddSearchMallModelReq(keyword = searchContent))
                    return true
                }
                return false
            }
        })
        swipeSearch.setOnRefreshListener {
            mSearchStorePresenter.loadSearch()
        }
        tvCancel.setOnClickListener {
            setResult(
                search_content_result_code,
                Intent().apply { putExtra(search_content_key, "") })
            finish()
        }
        ivDelete.setOnClickListener {
            mSearchStorePresenter.clearSearch()
        }
        mSearchHotAdapter.setOnItemClickListener { _, _, position ->
            setResult(
                search_content_result_code,
                Intent().apply { putExtra(search_content_key, mHotList[position].keyword) })
            finish()
        }
    }


    override fun clearSearch() {
        mSearchStorePresenter.loadSearch()
    }


    override fun loadAddSearch(mList: List<SearchItem>) {
        mHotList.clear()
        mHotList.addAll(mList)
        mSearchHotAdapter.notifyDataSetChanged()
        swipeSearch.isRefreshing = false
    }


    override fun addSearchSuccess() {
        setResult(
            search_content_result_code,
            Intent().apply { putExtra(search_content_key, searchContent) })
        finish()
    }

    override fun loadSearchStoreFail(throwable: Throwable) {
        handleError(throwable)
        swipeSearch.isRefreshing = false
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        const val search_content_request_code = 1000
        const val search_content_result_code = 1001
        const val search_content_key = "search_content_key"
        fun getInstance() = Bundle().apply { }
    }
}