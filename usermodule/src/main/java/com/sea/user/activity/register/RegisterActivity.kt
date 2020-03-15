package com.sea.user.activity.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.sea.user.R
import com.sea.user.activity.inform.FillInformActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.login.NLoginModelReq
import com.sea.user.activity.password.ForgetPasswordActivity
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
class RegisterActivity : BaseActivity(), RegisterContract.IRegisterView {

    private val registerPresenter by lazy {
        RegisterPresenter().apply {
            attachView(this@RegisterActivity)
        }
    }

    private lateinit var timeCountDown: TimeCountDown

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
            registerPresenter.loadRegister(NRegisterModelReq(userName, password, versionCode))
        }
        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    override fun loadRegisterSuccess() {
        startActivity(Intent(this, FillInformActivity::class.java))
    }

    override fun loadRegisterFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadVersionCodeSuccess() {

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