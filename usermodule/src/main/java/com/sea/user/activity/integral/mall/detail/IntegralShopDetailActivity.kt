package com.sea.user.activity.integral.mall.detail

import android.content.Intent
import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.integral.shop.ExchangeShopActivity
import kotlinx.android.synthetic.main.activity_integral_shop_detail.*

class IntegralShopDetailActivity : BaseActivity(),
    IntegralShopDetailContact.IIntegralShopDetailView {

    private val mIntegralShopDetailPresenter by lazy {
        IntegralShopDetailPresenter().apply {
            attachView(
                this@IntegralShopDetailActivity
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral_shop_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mIntegralShopDetailPresenter.loadIntegralShopDetail(NIntegralShopDetailModelReq())
    }

    private fun initListener() {
        tvTwoOnceExchange.setOnClickListener {
            startActivity(Intent(this, ExchangeShopActivity::class.java))
        }
    }

    override fun loadIntegralShopDetailSuccess(content: Any) {


    }

    override fun loadIntegralShopDetailFail(throwable: Throwable) {
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