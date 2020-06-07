package com.sea.user.activity.password

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.sea.user.R
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.register.RegisterActivity
import com.sea.user.presenter.version.NVersionCodeModelReq
import com.sea.user.presenter.version.VersionCodeContact
import com.sea.user.presenter.version.VersionCodePresenter
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.TimeCountDown
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.include_shop_eye.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class ForgetPasswordActivity : BaseActivity(), ForgetPasswordContract.IForgetPasswordView,
    VersionCodeContact.IVersionCodeView {

    private val forgetPasswordPresenter by lazy {
        ForgetPasswordPresenter().apply {
            attachView(this@ForgetPasswordActivity)
        }
    }

    private val versionCodePresenter by lazy { VersionCodePresenter().apply { attachView(this@ForgetPasswordActivity) } }
    private lateinit var timeCountDown: TimeCountDown

    //加密验证码
    private var authCode = ""


    private val nForgetPasswordModelReq = NForgetPasswordModelReq()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        timeCountDown = TimeCountDown(tvVersionCode, 60000, 1000)
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
        tvConfirm.setOnClickListener {
            val userName = evUserName.text.toString()
            val password = evPassword.text.toString()
            val versionCode = evVersionCode.text.toString()
            if (userName.isNullOrBlank()) {
                ToastUtils.show("账号不能为空")
                return@setOnClickListener
            }
            if (versionCode.isNullOrBlank()) {
                ToastUtils.show("验证码不能为空")
                return@setOnClickListener
            }
            if (password.isNullOrBlank()) {
                ToastUtils.show("密码不能为空")
                return@setOnClickListener
            }
            nForgetPasswordModelReq.phone = userName
            nForgetPasswordModelReq.password = password
            nForgetPasswordModelReq.input_code = versionCode
            nForgetPasswordModelReq.auth_code = authCode
            forgetPasswordPresenter.loadForgetPassword(nForgetPasswordModelReq)
        }
        tvVersionCode.setOnClickListener {
            timeCountDown.start()
            val phoneNumber = evUserName.text.toString()
            versionCodePresenter.loadVersionCode(NVersionCodeModelReq(phone = phoneNumber))
        }
        tvAccountLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun loadVersionCodeSuccess(versionCode: String) {
        this.authCode = versionCode
    }

    override fun loadVersionCodeFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadForgetPasswordSuccess() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun loadForgetPasswordFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = Bundle().apply {

        }
    }
}