package com.sea.user.presenter.user

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R
import com.sea.user.activity.login.UserInformModel

interface UserInformContact {

    interface IUserInformView : IBaseView {

        fun loadUserInformSuccess(userInformModel: UserInformModel)

        fun loadUserInformFail(throwable: Throwable)

    }

    interface IUserInformPresenter {
        fun loadUserInform()
    }
}