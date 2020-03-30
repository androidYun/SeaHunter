package com.sea.user.activity.mall.order.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import com.sea.user.activity.mall.order.detail.MallOrderDetailActivity
import com.sea.user.presenter.sea.order.cancel.CancelOrderContact
import com.sea.user.presenter.sea.order.cancel.CancelOrderPresenter
import com.sea.user.presenter.sea.order.cancel.NCancelOrderModelReq
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_shop_order_list.*

class ShopOrderListFragment : BaseFragment(), ShopOrderListContact.IShopOrderListView,
    CancelOrderContact.ICancelOrderView {

    private val mShopOrderListPresenter by lazy { ShopOrderListPresenter().apply { attachView(this@ShopOrderListFragment) } }

    private val mCancelOrderPresenter by lazy { CancelOrderPresenter().apply { attachView(this@ShopOrderListFragment) } }

    private val nShopOrderListReq = NShopOrderListModelReq()

    private val mShopOrderListList = mutableListOf<ShopOrderListItem>()

    private lateinit var mShopOrderListAdapter: ShopOrderListAdapter

    private val orderStatus by lazy { arguments?.getInt(order_status_key) ?: 0 }

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_shop_order_list, container, false)
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
        nShopOrderListReq.order_status = orderStatus
        mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
    }

    private fun initListener() {
        swipeShopOrderList.setOnRefreshListener {
            nShopOrderListReq.page_index = 1
            mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
        }
        mShopOrderListAdapter.setOnLoadMoreListener({
            if (nShopOrderListReq.page_index * nShopOrderListReq.page_size < totalCount) {
                mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
            } else {
                mShopOrderListAdapter.loadMoreEnd()
            }
        }, rvShopOrderList)
        mShopOrderListAdapter.setOnItemClickListener { _, view, position ->
            when (view.id) {
                R.id.tvLookDetail -> {
                    val shopOrderListItem = mShopOrderListList[position]
                    startActivity(Intent(context, MallOrderDetailActivity::class.java).apply {
                        putExtras(
                            MallOrderDetailActivity.getInstance(
                                shopOrderListItem
                            )
                        )
                    })
                }

            }
        }
            mShopOrderListAdapter.setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.tvLookDetail -> {
                        val shopOrderListItem = mShopOrderListList[position]
                        startActivity(Intent(context, MallOrderDetailActivity::class.java).apply {
                            putExtras(
                                MallOrderDetailActivity.getInstance(
                                    shopOrderListItem
                                )
                            )
                        })

                    }
                    R.id.tvCancelOrder -> {
                        mCancelOrderPresenter.loadCancelOrder(NCancelOrderModelReq(id = mShopOrderListList[position].id))
                    }
                    R.id.tvOncePay -> {
                        ToastUtils.show("理解付款")
                    }

                    R.id.tvLookLogistics -> {
                        ToastUtils.show("查看物流")
                    }
                    R.id.tvConfirmReceipt -> {
                        ToastUtils.show("确认收货")
                    }

                }

            }
        }

        override fun loadShopOrderListSuccess(mList: List<ShopOrderListItem>, totalCount: Int) {
            if (nShopOrderListReq.page_index == 1) {
                mShopOrderListList.clear()
            }
            this.totalCount = totalCount
            mShopOrderListList.addAll(mList)
            mShopOrderListAdapter.notifyDataSetChanged()
            mShopOrderListAdapter.loadMoreComplete()
            swipeShopOrderList.isRefreshing = false
            nShopOrderListReq.page_index++

        }

        override fun loadShopOrderListFail(throwable: Throwable) {
            handleError(throwable)
            swipeShopOrderList.isRefreshing = false
            mShopOrderListAdapter.loadMoreComplete()
        }

        override fun loadCancelOrderSuccess() {
            nShopOrderListReq.page_index = 1
            mShopOrderListPresenter.loadShopOrderList(nShopOrderListReq)
        }

        override fun loadCancelOrderFail(throwable: Throwable) {
            handleError(throwable)
        }

        override fun showLoading() {
            showProgressDialog()
        }

        override fun hideLoading() {
            hideProgressDialog()
        }

        companion object {
            private const val order_status_key = "order_status_key"
            fun getInstance(orderStatus: Int) = ShopOrderListFragment().apply {
                arguments = Bundle().apply {
                    putInt(order_status_key, orderStatus)
                }
            }
        }
    }