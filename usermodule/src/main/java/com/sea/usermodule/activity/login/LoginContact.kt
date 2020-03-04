package com.sea.usermodule.activity.login

import com.xhs.baselibrary.base.IBaseView
import com.sea.usermodule.R

interface LoginContact {

    interface ILoginView : IBaseView {

        fun loadLoginSuccess(content: Any)

        fun loadLoginFail(throwable: Throwable)

    }

    interface ILoginPresenter {
        fun loadLogin(nLoginModelReq: NLoginModelReq)
    }
}