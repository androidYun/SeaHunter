package com.sea.user.activity.set

import android.content.Intent
import android.os.Bundle
import com.sea.user.R
import com.sea.user.activity.address.list.AddressListActivity
import com.sea.user.activity.inform.FillInformActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.password.modify.ModifyPasswordActivity
import com.sea.user.activity.phone.ModifyPhoneActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_set.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class SetActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {

    }

    private fun initListener() {
        tvModifyData.setOnClickListener {
            startActivity(Intent(this, FillInformActivity::class.java))

        }
        tvModifyPassword.setOnClickListener {
            startActivity(Intent(this, ModifyPasswordActivity::class.java))

        }
        tvModifyPhone.setOnClickListener {
            startActivity(Intent(this, ModifyPhoneActivity::class.java))

        }
        tvAddress.setOnClickListener {
            startActivity(Intent(this, AddressListActivity::class.java))

        }
        tvMVersionUpdate.setOnClickListener {


        }
        tvBackAccount.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    companion object {
        fun getInstance() = Bundle().apply {

        }
    }
}