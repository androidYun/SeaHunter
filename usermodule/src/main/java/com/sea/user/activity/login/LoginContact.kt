package com.sea.user.activity.login

import com.xhs.baselibrary.base.IBaseView
import com.xhs.publicmodule.presenter.user.UserInformModel

interface LoginContact {

    interface ILoginView : IBaseView {

        fun loadLoginSuccess(content: UserInformModel,phone:String,password:String)

        fun loadLoginFail(throwable: Throwable)

    }

    interface ILoginPresenter {
        fun loadLogin(nLoginModelReq: NLoginModelReq)
    }
}