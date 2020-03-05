package com.sea.user.activity.password

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.sea.user.R
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.register.RegisterActivity
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.TimeCountDown
import com.xhs.baselibrary.utils.ToastUtils
import com.xhs.prison.model.NForgetPasswordModelReq
import kotlinx.android.synthetic.main.activity_forget_password.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class ForgetPasswordActivity : BaseActivity(), ForgetPasswordContract.IForgetPasswordView {

    private val forgetPasswordPresenter by lazy {
        ForgetPasswordPresenter().apply {
            attachView(this@ForgetPasswordActivity)
        }
    }
    private lateinit var timeCountDown: TimeCountDown

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
            evPassword.inputType =
                if (b) InputType.TYPE_TEXT_VARIATION_PASSWORD else InputType.TYPE_NUMBER_VARIATION_PASSWORD
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
            forgetPasswordPresenter.loadForgetPassword(NForgetPasswordModelReq())
        }
        tvVersionCode.setOnClickListener {
            timeCountDown.start()
        }
        tvAccountLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun loadForgetPasswordSuccess() {

    }

    override fun loadForgetPasswordFail(throwable: Throwable) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {
        fun getInstance() = Bundle().apply {

        }
    }
}