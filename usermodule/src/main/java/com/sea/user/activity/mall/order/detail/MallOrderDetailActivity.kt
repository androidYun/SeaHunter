package com.sea.user.activity.mall.order.detail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_mall_order_detail.*

class MallOrderDetailActivity : BaseActivity(), MallOrderDetailContact.IMallOrderDetailView {

    private val mMallOrderDetailPresenter by lazy {
        MallOrderDetailPresenter().apply {
            attachView(
                this@MallOrderDetailActivity
            )
        }
    }

    private lateinit var mMallOrderDetailAdapter: MallOrderDetailAdapter

    private val mMallOrderList = mutableListOf<NMallOrderDetailListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mall_order_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMallOrderDetailAdapter = MallOrderDetailAdapter(mMallOrderList)
        rvMallConfirmOrder.layoutManager = LinearLayoutManager(this)
        rvMallConfirmOrder.adapter = mMallOrderDetailAdapter
    }

    private fun initData() {
        mMallOrderDetailPresenter.loadMallOrderDetail(NMallOrderDetailModelReq())
    }

    private fun initListener() {

    }

    override fun loadMallOrderDetailSuccess(content: Any) {


    }

    override fun loadMallOrderDetailFail(throwable: Throwable) {
        handleError(throwable)
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