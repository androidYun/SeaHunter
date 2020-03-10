package com.sea.user.activity.mall.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_mall_list.*

class MallListActivity : BaseActivity(), MallListContact.IMallListView {

    private val mMallListPresenter by lazy { MallListPresenter().apply { attachView(this@MallListActivity) } }

    private val nMallListReq = NMallListModelReq()

    private val mMallListList = mutableListOf<MallListItem>()

    private lateinit var mMallListAdapter: MallListAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mall_list)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMallListAdapter = MallListAdapter(mMallListList)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap=FlexWrap.WRAP
        rvMallList.layoutManager = layoutManager
        rvMallList.adapter = mMallListAdapter
    }

    private fun initData() {
        mMallListList.add(MallListItem())
        mMallListList.add(MallListItem())
        mMallListList.add(MallListItem())
        mMallListList.add(MallListItem())
        mMallListList.add(MallListItem())
        mMallListList.add(MallListItem())
        mMallListList.add(MallListItem())
        mMallListPresenter.loadMallList(nMallListReq)
    }

    private fun initListener() {
        swipeMallList.setOnRefreshListener {
            mMallListPresenter.loadMallList(nMallListReq)
        }
        mMallListAdapter.setOnLoadMoreListener({
            if (nMallListReq.pageIndex * nMallListReq.pageSize < totalCount) {
                mMallListPresenter.loadMallList(nMallListReq)
            } else {
                mMallListAdapter.loadMoreEnd()
            }
        }, rvMallList)
    }

    override fun loadMallListSuccess(mList: List<MallListItem>, totalCount: Int) {
        if (nMallListReq.pageIndex == 1) {
            mMallListList.clear()
        }
        this.totalCount = totalCount
        mMallListList.addAll(mList)
        mMallListAdapter.notifyDataSetChanged()
        mMallListAdapter.loadMoreComplete()
        swipeMallList.isRefreshing = false
        nMallListReq.pageIndex++

    }

    override fun loadMallListFail(throwable: Throwable) {
        handleError(throwable)
        swipeMallList.isRefreshing
        mMallListAdapter.loadMoreComplete()
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