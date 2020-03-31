package com.sea.user.activity.password.modify

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ModifyPasswordContact {

    interface IModifyPasswordView : IBaseView {

        fun loadModifyPasswordSuccess(password: String)

        fun loadModifyPasswordFail(throwable: Throwable)

    }

    interface IModifyPasswordPresenter {
        fun loadModifyPassword(nModifyPasswordModelReq: NModifyPasswordModelReq)
    }
}