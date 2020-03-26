package com.sea.user.activity.integral.exchange

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.mall.order.list.NShopOrderListModelReq
import com.sea.user.activity.mall.order.list.ShopOrderListContact
import com.sea.user.activity.mall.order.list.ShopOrderListItem
import com.sea.user.activity.mall.order.list.ShopOrderListPresenter
import com.sea.user.presenter.sea.mall.MallListContact
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.MallListPresenter
import com.sea.user.presenter.sea.mall.NMallListModelReq
import kotlinx.android.synthetic.main.activity_exchange_list.*

class ExchangeListActivity : BaseActivity(),
    ShopOrderListContact.IShopOrderListView {


    private val mShopOrderListPresenter by lazy { ShopOrderListPresenter().apply { attachView(this@ExchangeListActivity) } }

    private val nShopOrderListReq = NShopOrderListModelReq()


    private val mShopOrderListList = mutableListOf<ShopOrderListItem>()

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
        mExchangeListAdapter = ExchangeListAdapter(mShopOrderListList)
        rvExchangeList.layoutManager = LinearLayoutManager(this)
        rvExchangeList.adapter = mExchangeListAdapter
    }

    private fun initData() {
        nShopOrderListReq.order_type = 2
        nShopOrderListReq.order_status = 0
        mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
    }

    private fun initListener() {
        swipeExchangeList.setOnRefreshListener {
            nShopOrderListReq.page_index=1
            mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
        }
        mExchangeListAdapter.setOnLoadMoreListener({
            if (nShopOrderListReq.page_index * nShopOrderListReq.page_size < totalCount) {
                mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
            } else {
                mExchangeListAdapter.loadMoreEnd()
            }
        }, rvExchangeList)
    }



    override fun loadShopOrderListSuccess(mList: List<ShopOrderListItem>, totalCount: Int) {
        if (nShopOrderListReq.page_index == 1) {
            mShopOrderListList.clear()
        }
        this.totalCount = totalCount
        mShopOrderListList.addAll(mList)
        mExchangeListAdapter.notifyDataSetChanged()
        mExchangeListAdapter.loadMoreComplete()
        swipeExchangeList.isRefreshing = false
        nShopOrderListReq.page_index++
    }

    override fun loadShopOrderListFail(throwable: Throwable) {
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