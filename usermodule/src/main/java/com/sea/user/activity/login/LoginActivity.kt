package com.sea.user.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.sea.user.R
import com.sea.user.activity.password.ForgetPasswordActivity
import com.sea.user.activity.register.RegisterActivity
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
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

    }

    private fun initListener() {
        cbEye.setOnCheckedChangeListener { _, b ->
            evPassword.inputType =
                if (b) InputType.TYPE_TEXT_VARIATION_PASSWORD else InputType.TYPE_NUMBER_VARIATION_PASSWORD
        }
        tvLogin.setOnClickListener {
            val userName = evUserName.text.toString()
            val password = evPassword.text.toString()
            if (userName.isNullOrBlank()) {
                ToastUtils.show("账号不能为空")
                return@setOnClickListener
            }
            if (password.isNullOrBlank()) {
                ToastUtils.show("密码不能为空")
                return@setOnClickListener
            }
            mLoginPresenter.loadLogin(NLoginModelReq())
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
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