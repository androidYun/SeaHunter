package com.sea.hunter.router

import android.content.Intent
import com.sea.custom.CustomMainActivity
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.sea.user.activity.address.list.AddressListActivity
import com.sea.user.activity.inform.FillInformActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.password.modify.ModifyPasswordActivity
import com.sea.user.activity.phone.ModifyPhoneActivity
import com.xhs.baselibrary.BaseApplication
import com.sea.publicmodule.router.ISeaRouter


class SeaRouterImpl : ISeaRouter {



    override fun loginOut() {
        BaseApplication.getsInstance()
            .startActivity(
                Intent(
                    BaseApplication.getsInstance(),
                    LoginActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
    }

    override fun modifyPassword() {
        BaseApplication.getsInstance()
            .startActivity(
                Intent(
                    BaseApplication.getsInstance(),
                    ModifyPasswordActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
    }

    override fun modifyPhone() {
        BaseApplication.getsInstance()
            .startActivity(
                Intent(
                    BaseApplication.getsInstance(),
                    ModifyPhoneActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
    }

    override fun mineAddress() {
        BaseApplication.getsInstance()
            .startActivity(
                Intent(
                    BaseApplication.getsInstance(),
                    AddressListActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
    }

    override fun modifyUserInform() {
        BaseApplication.getsInstance()
            .startActivity(
                Intent(
                    BaseApplication.getsInstance(),
                    FillInformActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
    }

    override fun jumpMainActivity() {
        BaseApplication.getsInstance()
            .startActivity(
                Intent(
                    BaseApplication.getsInstance(),
                    CustomMainActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
    }
}