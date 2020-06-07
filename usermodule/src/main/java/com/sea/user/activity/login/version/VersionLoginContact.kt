package com.sea.user.activity.login.version

import com.sea.publicmodule.presenter.user.UserInformModel
import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface VersionLoginContact {

    interface IVersionLoginView : IBaseView {

        fun loadVersionLoginSuccess(userInformModel: UserInformModel,
                                    phoneNumber: String )

        fun loadVersionLoginFail(throwable: Throwable)

    }

    interface IVersionLoginPresenter {
        fun loadVersionLogin(nVersionLoginModelReq: NVersionLoginModelReq)
    }
}