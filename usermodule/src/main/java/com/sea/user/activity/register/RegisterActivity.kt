package com.sea.user.activity.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.sea.user.R
import com.sea.user.activity.inform.FillInformActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.login.NLoginModelReq
import com.sea.user.activity.password.ForgetPasswordActivity
import com.sea.user.presenter.version.NVersionCodeModelReq
import com.sea.user.presenter.version.VersionCodeContact
import com.sea.user.presenter.version.VersionCodePresenter
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.TimeCountDown
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.activity_user_login.cbEye
import kotlinx.android.synthetic.main.activity_user_login.evPassword
import kotlinx.android.synthetic.main.activity_user_login.evUserName
import kotlinx.android.synthetic.main.activity_user_login.tvForgetPassword
import kotlinx.android.synthetic.main.activity_user_login.tvLogin


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class RegisterActivity : BaseActivity(), RegisterContract.IRegisterView,
    VersionCodeContact.IVersionCodeView {

    private val registerPresenter by lazy {
        RegisterPresenter().apply {
            attachView(this@RegisterActivity)
        }
    }

    private val versionCodePresenter by lazy { VersionCodePresenter().apply { attachView(this@RegisterActivity) } }

    private lateinit var timeCountDown: TimeCountDown

    //加密验证码
    private var authCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
            evPassword.inputType =
                if (b) InputType.TYPE_TEXT_VARIATION_PASSWORD else InputType.TYPE_NUMBER_VARIATION_PASSWORD
        }
        tvNextStep.setOnClickListener {
            val phoneNumber = evPhoneNumber.text.toString()
            val password = evPassword.text.toString()
            val versionCode = evVersionCode.text.toString()
            if (phoneNumber.isNullOrBlank()) {
                ToastUtils.show("手机号不能为空")
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
            if (authCode.isNullOrBlank()) {
                ToastUtils.show("请获取验证码")
                return@setOnClickListener
            }
            registerPresenter.loadRegister(
                NRegisterModelReq(
                    phoneNumber,
                    password,
                    versionCode,
                    authCode
                )
            )
        }
        tvLogin.setOnClickListener {
            startActivity(Intent(this, FillInformActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
        tvVersionCode.setOnClickListener {
            val phoneNumber = evPhoneNumber.text.toString()
            versionCodePresenter.loadVersionCode(NVersionCodeModelReq(phone = phoneNumber))
        }
    }

    override fun loadRegisterSuccess() {
        startActivity(Intent(this, FillInformActivity::class.java))
    }

    override fun loadRegisterFail(throwable: Throwable) {
        handleError(throwable)
    }


    override fun loadVersionCodeSuccess(authCode: String) {
        this.authCode = authCode
        timeCountDown.start()
    }

    override fun loadVersionCodeFail(throwable: Throwable) {
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