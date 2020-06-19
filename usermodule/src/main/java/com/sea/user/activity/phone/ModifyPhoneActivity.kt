package com.sea.user.activity.phone

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.sea.user.presenter.version.NVersionCodeModelReq
import com.sea.user.presenter.version.VersionCodeContact
import com.sea.user.presenter.version.VersionCodePresenter
import com.xhs.baselibrary.utils.TimeCountDown
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_modity_phone.*

class ModifyPhoneActivity : BaseActivity(), ModifyPhoneContact.IModifyPhoneView,
    VersionCodeContact.IVersionCodeView {

    private val mModifyPhonePresenter by lazy { ModifyPhonePresenter().apply { attachView(this@ModifyPhoneActivity) } }


    private val versionCodePresenter by lazy { VersionCodePresenter().apply { attachView(this@ModifyPhoneActivity) } }


    private lateinit var timeCountDown: TimeCountDown

    //加密验证码
    private var authCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modity_phone)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        timeCountDown = TimeCountDown(tvVersionCode, 60000, 1000)
    }

    private fun initListener() {
        tvConfirm.setOnClickListener {
            val phoneNumber = tvConfirm.text.toString()
            val versionCode = evVersionCode.text.toString()
            val newPhoneNumber = evPhoneNumber.text.toString()
            if (phoneNumber.isNullOrBlank()) {
                ToastUtils.show("手机号不能为空")
                return@setOnClickListener
            }
            if (versionCode.isNullOrBlank()) {
                ToastUtils.show("验证码不能为空")
                return@setOnClickListener
            }
            if (newPhoneNumber.isNullOrBlank()) {
                ToastUtils.show("新手机号不能为空")
                return@setOnClickListener
            }
            if (authCode.isNullOrBlank()) {
                ToastUtils.show("请先获取验证码")
                return@setOnClickListener
            }
            mModifyPhonePresenter.loadModifyPhone(
                NModifyPhoneModelReq(
                    mobile = phoneNumber,
                    input_code = versionCode,
                    auth_code = authCode
                )
            )
        }
        tvVersionCode.setOnClickListener {
            val phoneNumber = evUserName.text.toString()
            if (phoneNumber.isNullOrBlank()) {
                ToastUtils.show("手机号不能为空")
                return@setOnClickListener
            }
            versionCodePresenter.loadVersionCode(NVersionCodeModelReq(phone = phoneNumber))
        }
    }

    override fun loadModifyPhoneSuccess(phoneNumber: String) {
        UserInformSpUtils.setPhoneNumber(phoneNumber)
        finish()

    }

    override fun loadModifyPhoneFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadVersionCodeSuccess(versionCode: String) {
        this.authCode = versionCode
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
        fun getInstance() = Bundle().apply { }
    }
}