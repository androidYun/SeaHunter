package com.sea.user.activity.login

import com.xhs.baselibrary.base.IBaseView

interface LoginContact {

    interface ILoginView : IBaseView {

        fun loadLoginSuccess(content: Any)

        fun loadLoginFail(throwable: Throwable)

    }

    interface ILoginPresenter {
        fun loadLogin(nLoginModelReq: NLoginModelReq)
    }
}