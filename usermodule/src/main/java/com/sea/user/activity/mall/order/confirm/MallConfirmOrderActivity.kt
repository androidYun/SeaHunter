package com.sea.user.activity.mall

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_mall_confirm_order.*

class MallConfirmOrderActivity : BaseActivity(), MallConfirmOrderContact.IMallConfirmOrderView {

    private val mMallConfirmOrderPresenter by lazy { MallConfirmOrderPresenter().apply { attachView(this@MallConfirmOrderActivity) } }

    private val nMallConfirmOrderReq = NMallConfirmOrderModelReq()

    private val mMallConfirmOrderList = mutableListOf<MallConfirmOrderItem>()

    private lateinit var mMallConfirmOrderAdapter: MallConfirmOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mall_confirm_order)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMallConfirmOrderAdapter = MallConfirmOrderAdapter(mMallConfirmOrderList)
        rvMallConfirmOrder.layoutManager = LinearLayoutManager(this)
        rvMallConfirmOrder.adapter = mMallConfirmOrderAdapter
    }

    private fun initData() {
        mMallConfirmOrderPresenter.loadMallConfirmOrder(nMallConfirmOrderReq)
    }

    private fun initListener() {
        swipeMallConfirmOrder.setOnRefreshListener {
            mMallConfirmOrderPresenter.loadMallConfirmOrder(nMallConfirmOrderReq)
        }
    }

    override fun loadMallConfirmOrderSuccess(mList: List<MallConfirmOrderItem>) {
        swipeMallConfirmOrder.isRefreshing = false
        mMallConfirmOrderList.clear()
        mMallConfirmOrderList.addAll(mList)
        mMallConfirmOrderAdapter.notifyDataSetChanged()

    }

    override fun loadMallConfirmOrderFail(throwable: Throwable) {
        handleError(throwable)
        swipeMallConfirmOrder.isRefreshing = false
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