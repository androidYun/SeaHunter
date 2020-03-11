package com.sea.user.activity.mall.order.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import kotlinx.android.synthetic.main.fragment_shop_order_list.*
import com.xhs.baselibrary.base.BaseFragment

class ShopOrderListFragment : BaseFragment(), ShopOrderListContact.IShopOrderListView {

    private val mShopOrderListPresenter by lazy { ShopOrderListPresenter().apply { attachView(this@ShopOrderListFragment) } }

    private val nShopOrderListReq = NShopOrderListModelReq()

    private val mShopOrderListList = mutableListOf<ShopOrderListItem>()

    private lateinit var mShopOrderListAdapter: ShopOrderListAdapter

    private var totalCount = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_shop_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mShopOrderListAdapter = ShopOrderListAdapter(mShopOrderListList)
        rvShopOrderList.layoutManager = LinearLayoutManager(context)
        rvShopOrderList.adapter = mShopOrderListAdapter
    }

    private fun initData() {
        mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
    }

    private fun initListener() {
        swipeShopOrderList.setOnRefreshListener {
            mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
        }
        mShopOrderListAdapter.setOnLoadMoreListener({
            if (nShopOrderListReq.pageIndex * nShopOrderListReq.pageSize < totalCount) {
                mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
            } else {
                mShopOrderListAdapter.loadMoreEnd()
            }
        }, rvShopOrderList)
    }

    override fun loadShopOrderListSuccess(mList: List<ShopOrderListItem>, totalCount: Int) {
        if (nShopOrderListReq.pageIndex == 1) {
            mShopOrderListList.clear()
        }
        this.totalCount = totalCount
        mShopOrderListList.addAll(mList)
        mShopOrderListAdapter.notifyDataSetChanged()
        mShopOrderListAdapter.loadMoreComplete()
        swipeShopOrderList.isRefreshing = false
        nShopOrderListReq.pageIndex++

    }

    override fun loadShopOrderListFail(throwable: Throwable) {
        handleError(throwable)
        swipeShopOrderList.isRefreshing
        mShopOrderListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = ShopOrderListFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}