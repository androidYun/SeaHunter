package com.sea.user.activity.wallet.reflect

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_reflect.*

class ReflectActivity : BaseActivity(), ReflectContact.IReflectView {

    private val mReflectPresenter by lazy { ReflectPresenter().apply { attachView(this@ReflectActivity) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflect)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mReflectPresenter.loadReflect(NReflectModelReq())
    }

    private fun initListener() {

    }

    override fun loadReflectSuccess(content: Any) {


    }

    override fun loadReflectFail(throwable: Throwable) {
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