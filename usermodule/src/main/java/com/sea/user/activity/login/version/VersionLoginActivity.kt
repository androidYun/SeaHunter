package com.sea.user.activity.login.version

import android.os.Bundle
import com.sea.publicmodule.presenter.user.UserInformModel
import com.sea.publicmodule.router.RouterManager
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.sea.user.R
import com.sea.user.presenter.version.NVersionCodeModelReq
import com.sea.user.presenter.version.VersionCodeContact
import com.sea.user.presenter.version.VersionCodePresenter
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.TimeCountDown
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_version_login.*

class VersionLoginActivity : BaseActivity(), VersionLoginContact.IVersionLoginView,
    VersionCodeContact.IVersionCodeView {

    private val mVersionLoginPresenter by lazy { VersionLoginPresenter().apply { attachView(this@VersionLoginActivity) } }

    private val versionCodePresenter by lazy { VersionCodePresenter().apply { attachView(this@VersionLoginActivity) } }

    private val nVersionLoginModelReq = NVersionLoginModelReq()

    private lateinit var timeCountDown: TimeCountDown

    //加密验证码
    private var authCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_login)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        UserInformSpUtils.clearUserInform()//清除用户
    }

    private fun initData() {
        timeCountDown = TimeCountDown(tvVersionCode, 60000, 1000)
    }

    private fun initListener() {
        tvLogin.setOnClickListener {
            val userName = evUserName.text.toString()
            val versionCode = evVersionCode.text.toString()
            if (userName.isNullOrBlank()) {
                ToastUtils.show("手机号不能为空")
                return@setOnClickListener
            }
            if (versionCode.isNullOrBlank()) {
                ToastUtils.show("验证码不能为空")
                return@setOnClickListener
            }
            nVersionLoginModelReq.phone = userName
            nVersionLoginModelReq.input_code = versionCode
            nVersionLoginModelReq.auth_code = authCode
            mVersionLoginPresenter.loadVersionLogin(nVersionLoginModelReq)
        }
        tvVersionCode.setOnClickListener {
            val phoneNumber = evUserName.text.toString()
            versionCodePresenter.loadVersionCode(NVersionCodeModelReq(phone = phoneNumber))
        }
    }

    override fun loadVersionLoginSuccess(userInformModel: UserInformModel, phoneNumber: String) {
        UserInformSpUtils.setUserInformModel(userInformModel = userInformModel)
        UserInformSpUtils.setPhoneNumber(phoneNumber)
        RouterManager.seaRouter?.jumpMainActivity()
        finish()
    }

    override fun loadVersionCodeFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadVersionLoginFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadVersionCodeSuccess(authCode: String) {
        this.authCode = authCode
        timeCountDown.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timeCountDown.cancel()
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