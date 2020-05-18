package com.sea.publicmodule.presenter.user

import com.xhs.baselibrary.base.IBaseView

interface UserInformContact {

    interface IUserInformView : IBaseView {

        fun loadUserInformSuccess(userInformModel: UserInformModel)

        fun loadUserInformFail(throwable: Throwable)

    }

    interface IUserInformPresenter {
        fun loadUserInform()
    }
}