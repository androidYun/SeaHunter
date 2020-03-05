package com.sea.user.activity.inform

import android.content.Intent
import android.os.Bundle
import com.sea.user.R
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.password.ForgetPasswordActivity
import com.sea.user.activity.register.RegisterActivity
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.prison.model.NFillInformReq
import kotlinx.android.synthetic.main.activity_fill_inform.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class FillInformActivity : BaseActivity(), FillInformContract.IFillInformView {

    private val fillinformPresenter by lazy {
        FillInformPresenter().apply {
            attachView(this@FillInformActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_inform)
        initView()
        initData()
    }


    private fun initView() {

    }

    private fun initData() {

    }

    private fun initListener() {

        tvConfirm.setOnClickListener {
            fillinformPresenter.loadFillInform(NFillInformReq())
        }
        tvAccountLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    override fun loadFillInformSuccess() {

    }

    override fun loadFillInformFail(throwable: Throwable) {

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