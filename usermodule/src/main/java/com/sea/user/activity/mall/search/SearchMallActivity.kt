package com.sea.user.activity.mall.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.user.R
import com.sea.user.activity.mall.list.MallListAdapter
import com.sea.user.presenter.sea.mall.MallListContact
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.MallListPresenter
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_search_mall.*

class SearchMallActivity : BaseActivity(), SearchMallContact.ISearchMallView,
    MallListContact.IMallListView {
    private val mSearchStorePresenter by lazy { SearchMallPresenter().apply { attachView(this@SearchMallActivity) } }

    private val mMallListPresenter by lazy { MallListPresenter().apply { attachView(this@SearchMallActivity) } }

    private val nMallListReq = NMallListModelReq(page_size = 20, page_index = 1)

    private val mMallListList = mutableListOf<MallListItem>()

    private lateinit var mMallListAdapter: MallListAdapter

    private var totalCount = 0

    private lateinit var mSearchHotAdapter: SearchHotAdapter

    private val mHotList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_mall)
        initView()
        initData()
        initListener()
    }

    private fun initData() {
        mSearchHotAdapter = SearchHotAdapter(mHotList)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rvHotSearch.layoutManager = layoutManager
        rvHotSearch.adapter = mSearchHotAdapter
        /*商品列表*/
        mMallListAdapter = MallListAdapter(mMallListList)
        val mallLayoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        rvSearchMall.layoutManager = mallLayoutManager
        rvSearchMall.adapter = mMallListAdapter
        mSearchStorePresenter.loadHotSearch()
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
                    nMallListReq.page_index=1
                    mSearchStorePresenter.loadAddSearch(NAddSearchMallModelReq(keyword = searchContent))
                    mMallListPresenter.loadMallList(NMallListModelReq(key = searchContent))

                    return true
                }
                return false
            }
        })
        mSearchHotAdapter.setOnItemClickListener { _, _, position ->
            nMallListReq.page_index=1
            mMallListPresenter.loadMallList(NMallListModelReq(key = mHotList[position]))
        }
        swipeSearchMall.setOnRefreshListener {
            nMallListReq.page_index=1
            mMallListPresenter.loadMallList(NMallListModelReq(key = searchContent))
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    override fun loadHotSearchSuccess(mList: List<String>) {
        mHotList.clear()
        mHotList.addAll(mList)
        mHotList.add("鱼")
        mHotList.add("鱼")
        mHotList.add("鱼")
        mHotList.add("鱼")
        mHotList.add("鱼")
        mSearchHotAdapter.notifyDataSetChanged()
    }


    override fun loadMallListSuccess(mList: List<MallListItem>, totalCount: Int) {
        if (nMallListReq.page_index == 1) {
            mMallListList.clear()
        }
        this.totalCount = totalCount
        mMallListList.addAll(mList)
        mMallListAdapter.notifyDataSetChanged()
        mMallListAdapter.loadMoreEnd()
        swipeSearchMall.isRefreshing = false
        nMallListReq.page_index++

    }

    override fun loadMallListFail(throwable: Throwable) {
        handleError(throwable)
        swipeSearchMall.isRefreshing
        mMallListAdapter.loadMoreEnd()
    }

    override fun clearSearch() {

    }

    override fun loadAddSearch() {

    }

    override fun loadSearchStoreFail(throwable: Throwable) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}