package com.sea.user.activity.shop.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_shop_order.*

class ShopOrderFragment : BaseFragment(), ShopOrderContact.IShopOrderView {

    private val mShopOrderPresenter by lazy { ShopOrderPresenter().apply { attachView(this@ShopOrderFragment) } }

    private val nShopOrderReq = NShopOrderModelReq()

    private val mShopOrderList = mutableListOf<ShopOrderItem>()

    private lateinit var mShopOrderAdapter: ShopOrderAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_shop_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mShopOrderAdapter = ShopOrderAdapter(mShopOrderList)
        rvShopOrder.layoutManager = LinearLayoutManager(context)
        rvShopOrder.adapter = mShopOrderAdapter
    }

    private fun initData() {
        //mShopOrderPresenter.loadShopOrder(nShopOrderReq)
    }

    private fun initListener() {
        swipeShopOrder.setOnRefreshListener {
            mShopOrderPresenter.loadShopOrder(nShopOrderReq)
        }
        mShopOrderAdapter.setOnLoadMoreListener({
            if (nShopOrderReq.pageIndex * nShopOrderReq.pageSize < totalCount) {
                mShopOrderPresenter.loadShopOrder(nShopOrderReq)
            } else {
                mShopOrderAdapter.loadMoreEnd()
            }
        }, rvShopOrder)
    }

    override fun loadShopOrderSuccess(mList: List<ShopOrderItem>, totalCount: Int) {
        if (nShopOrderReq.pageIndex == 1) {
            mShopOrderList.clear()
        }
        this.totalCount = totalCount
        mShopOrderList.addAll(mList)
        mShopOrderAdapter.notifyDataSetChanged()
        mShopOrderAdapter.loadMoreComplete()
        swipeShopOrder.isRefreshing = false
        nShopOrderReq.pageIndex++

    }

    override fun loadShopOrderFail(throwable: Throwable) {
        handleError(throwable)
        swipeShopOrder.isRefreshing
        mShopOrderAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = ShopOrderFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}