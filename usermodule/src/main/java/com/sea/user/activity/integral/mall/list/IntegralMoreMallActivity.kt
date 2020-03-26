package com.sea.user.activity.integral.mall.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import com.sea.user.activity.integral.mall.IntegralMallAdapter
import com.sea.user.presenter.sea.mall.MallListContact
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.MallListPresenter
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_integral_mall.*

class IntegralMoreMallActivity : BaseActivity(), MallListContact.IMallListView {


    private val mMallListPresenter by lazy { MallListPresenter().apply { attachView(this@IntegralMoreMallActivity) } }

    private val nMallListReq = NMallListModelReq(page_size = 20, page_index = 1)

    private val mMallListList = mutableListOf<MallListItem>()


    private lateinit var mIntegralMallAdapter: IntegralMallAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_integral_mall)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mIntegralMallAdapter = IntegralMallAdapter(mMallListList)
        rvIntegralMall.layoutManager = LinearLayoutManager(this)
        rvIntegralMall.adapter = mIntegralMallAdapter
    }

    private fun initData() {
        mMallListPresenter.loadMallList(nMallListReq)
    }

    private fun initListener() {
        mIntegralMallAdapter.setOnLoadMoreListener({
            if (nMallListReq.page_index * nMallListReq.page_size < totalCount) {
                mMallListPresenter.loadMallList(nMallListReq)
            } else {
                mIntegralMallAdapter.loadMoreEnd()
            }
        }, rvIntegralMall)
    }

    override fun loadMallListSuccess(mList: List<MallListItem>, totalCount: Int) {
        if (nMallListReq.page_index == 1) {
            mMallListList.clear()
        }
        this.totalCount = totalCount
        mMallListList.addAll(mList)
        mIntegralMallAdapter.notifyDataSetChanged()
        mIntegralMallAdapter.loadMoreComplete()
        swipeIntegralMallList.isRefreshing = false
        nMallListReq.page_index++
    }

    override fun loadMallListFail(throwable: Throwable) {
        handleError(throwable)
        swipeIntegralMallList.isRefreshing
        mIntegralMallAdapter.loadMoreComplete()
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