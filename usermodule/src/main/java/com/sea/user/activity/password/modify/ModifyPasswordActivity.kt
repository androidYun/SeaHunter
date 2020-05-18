package com.sea.user.activity.password.modify

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.sea.user.R
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_modify_password.*

class ModifyPasswordActivity : BaseActivity(), ModifyPasswordContact.IModifyPasswordView {

    private val mModifyPasswordPresenter by lazy { ModifyPasswordPresenter().apply { attachView(this@ModifyPasswordActivity) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {

    }

    private fun initListener() {
        cbOldEye.setOnCheckedChangeListener { _, b ->
            evOldPassword.transformationMethod = if (b) {
                //否则隐藏密码
                PasswordTransformationMethod.getInstance()
            } else {
                //如果选中，显示密码
                HideReturnsTransformationMethod.getInstance()
            }
        }
        cbNewEye.setOnCheckedChangeListener { _, b ->
            evNewPassword.transformationMethod = if (b) {
                //否则隐藏密码
                PasswordTransformationMethod.getInstance()
            } else {
                //如果选中，显示密码
                HideReturnsTransformationMethod.getInstance()
            }
        }
        cbConfirmNewEye.setOnCheckedChangeListener { _, b ->
            evConfirmNewPassword.transformationMethod = if (b) {
                //否则隐藏密码
                PasswordTransformationMethod.getInstance()
            } else {
                //如果选中，显示密码
                HideReturnsTransformationMethod.getInstance()
            }
        }
        tvConfirm.setOnClickListener {
            val oldPwd = evOldPassword.text.toString()
            val newPwd = evNewPassword.text.toString()
            val confirmNewPwd = evConfirmNewPassword.text.toString()
            if (oldPwd.isNullOrBlank()) {
                ToastUtils.show("请输入旧密码")
                return@setOnClickListener
            }
            if (newPwd.isNullOrBlank()) {
                ToastUtils.show("请输入新密码")
                return@setOnClickListener
            }
            if (confirmNewPwd.isNullOrBlank()) {
                ToastUtils.show("请输入确认密码")
                return@setOnClickListener
            }
            if (confirmNewPwd != newPwd) {
                ToastUtils.show("两次密码不一致，请重新输入")
                return@setOnClickListener
            }
            mModifyPasswordPresenter.loadModifyPassword(NModifyPasswordModelReq(old_pwd = oldPwd, new_pwd = newPwd))
        }
    }

    override fun loadModifyPasswordSuccess(password: String) {
        UserInformSpUtils.getPassword(password)
        finish()

    }

    override fun loadModifyPasswordFail(throwable: Throwable) {
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