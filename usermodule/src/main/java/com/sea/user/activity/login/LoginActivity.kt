package com.sea.user.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.sea.user.R
import com.sea.user.activity.main.SeaMainActivity
import com.sea.user.activity.mall.SeaFoodMallActivity
import com.sea.user.activity.password.ForgetPasswordActivity
import com.sea.user.activity.register.RegisterActivity
import com.sea.user.utils.DeviceUtils
import com.xhs.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import com.xhs.publicmodule.presenter.user.UserInformModel
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.include_shop_eye.*

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
            evPassword.transformationMethod = if (b) {
                //否则隐藏密码
                PasswordTransformationMethod.getInstance()
            } else {
                //如果选中，显示密码
                HideReturnsTransformationMethod.getInstance()
            }

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
            mLoginPresenter.loadLogin(NLoginModelReq(phone = userName, password = password))
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    override fun loadLoginSuccess(userInformModel: UserInformModel, phoneNumber: String, password: String) {
        UserInformSpUtils.setUserInformModel(userInformModel = userInformModel)
        UserInformSpUtils.setPhoneNumber(phoneNumber)
        UserInformSpUtils.setPassword(password)
        if (DeviceUtils.isTabletDevice()) {
            startActivity(Intent(this, SeaFoodMallActivity::class.java))
        } else {
            startActivity(Intent(this, SeaMainActivity::class.java))
        }

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