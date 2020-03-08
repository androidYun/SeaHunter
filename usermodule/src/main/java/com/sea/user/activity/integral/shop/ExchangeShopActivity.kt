package com.sea.user.activity.integral.shop

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R

class ExchangeShopActivity : BaseActivity(), ExchangeShopContact.IExchangeShopView {

    private val mExchangeShopPresenter by lazy { ExchangeShopPresenter().apply { attachView(this@ExchangeShopActivity) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_shop)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mExchangeShopPresenter.loadExchangeShop(NExchangeShopModelReq())
    }

    private fun initListener() {

    }

    override fun loadExchangeShopSuccess(content: Any) {


    }

    override fun loadExchangeShopFail(throwable: Throwable) {
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