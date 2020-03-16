package com.sea.user.presenter.user

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface UserInformContact {

    interface IUserInformView : IBaseView {

        fun loadUserInformSuccess(content: Any)

        fun loadUserInformFail(throwable: Throwable)

    }

    interface IUserInformPresenter {
        fun loadUserInform()
    }
}