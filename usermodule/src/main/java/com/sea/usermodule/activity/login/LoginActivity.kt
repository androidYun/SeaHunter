package com.sea.usermodule.activity.login

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.usermodule.R
import kotlinx.android.synthetic.main.activity_user_login.*

class LoginActivity : BaseActivity(), LoginContact.ILoginView {

    private val mLoginPresenter by lazy { LoginPresenter().apply { attachView(this@LoginActivity) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mLoginPresenter.loadLogin("")
    }

    private fun initListener() {

    }

    override fun loadLoginSuccess(content: Any) {


    }

    override fun loadLoginFail(throwable: Throwable) {
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