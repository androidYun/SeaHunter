package com.sea.user.activity.phone

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import kotlinx.android.synthetic.main.activity_modity_phone.*

class ModifyPhoneActivity : BaseActivity(), ModifyPhoneContact.IModifyPhoneView {

    private val mModifyPhonePresenter by lazy { ModifyPhonePresenter().apply { attachView(this@ModifyPhoneActivity) } }

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

    }

    private fun initListener() {
        val phoneNumber = tvConfirm.text.toString()
        tvConfirm.setOnClickListener {
            mModifyPhonePresenter.loadModifyPhone(NModifyPhoneModelReq(mobile = phoneNumber))
        }
    }

    override fun loadModifyPhoneSuccess(phoneNumber: String) {
        UserInformSpUtils.setPhoneNumber(phoneNumber)
        finish()

    }

    override fun loadModifyPhoneFail(throwable: Throwable) {
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