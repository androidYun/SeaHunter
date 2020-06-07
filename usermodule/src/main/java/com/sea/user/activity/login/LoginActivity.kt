package com.sea.user.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.MotionEvent
import com.crashlytics.android.Crashlytics
import com.sea.user.R
import com.sea.user.activity.main.SeaMainActivity
import com.sea.user.activity.mall.SeaFoodMallActivity
import com.sea.user.activity.password.ForgetPasswordActivity
import com.sea.user.activity.register.RegisterActivity
import com.sea.user.utils.DeviceUtils
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import com.sea.publicmodule.presenter.user.UserInformModel
import com.sea.publicmodule.router.RouterManager
import com.sea.user.activity.login.version.VersionLoginActivity
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.include_shop_eye.*
import java.lang.NullPointerException
import java.util.concurrent.TimeoutException

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
//        if (!UserInformSpUtils.getPhoneNumber().isNullOrBlank()) {
//            evUserName.setText(UserInformSpUtils.getPhoneNumber())
//        }
//        if (!UserInformSpUtils.getPassword().isNullOrBlank()) {
//            evPassword.setText(UserInformSpUtils.getPassword())
//        }
    }

    private fun initData() {
        UserInformSpUtils.clearUserInform()//清除用户
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
        tvSmsLogin.setOnClickListener {
            startActivity(Intent(this, VersionLoginActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    override fun loadLoginSuccess(
        userInformModel: UserInformModel,
        phoneNumber: String,
        password: String
    ) {
        UserInformSpUtils.setUserInformModel(userInformModel = userInformModel)
        UserInformSpUtils.setPhoneNumber(phoneNumber)
        UserInformSpUtils.setPassword(password)
        RouterManager.seaRouter?.jumpMainActivity()
        finish()

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else {
            super.dispatchKeyEvent(event)
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