package com.sea.user.activity.integral.mall

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_integral_mall.*

class IntegralMallActivity : BaseActivity(), IntegralMallContact.IIntegralMallView {

    private val mIntegralMallPresenter by lazy { IntegralMallPresenter().apply { attachView(this@IntegralMallActivity) } }

    private val nIntegralMallReq = NIntegralMallModelReq()

    private val mIntegralMallList = mutableListOf<IntegralMallItem>()

    private lateinit var mIntegralMallAdapter: IntegralMallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral_mall)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mIntegralMallAdapter = IntegralMallAdapter(mIntegralMallList)
        rvIntegralMall.layoutManager = LinearLayoutManager(this)
        rvIntegralMall.adapter = mIntegralMallAdapter
    }

    private fun initData() {
        mIntegralMallPresenter.loadIntegralMall(nIntegralMallReq)
    }

    private fun initListener() {

    }

    override fun loadIntegralMallSuccess(mList: List<IntegralMallItem>) {
        mIntegralMallList.clear()
        mIntegralMallList.addAll(mList)
        mIntegralMallAdapter.notifyDataSetChanged()

    }

    override fun loadIntegralMallFail(throwable: Throwable) {
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