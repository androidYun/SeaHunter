package com.sea.user.activity.phone

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ModifyPhoneContact {

    interface IModifyPhoneView : IBaseView {

        fun loadModifyPhoneSuccess(phoneNumber:String)

        fun loadModifyPhoneFail(throwable: Throwable)

    }

    interface IModifyPhonePresenter {
        fun loadModifyPhone(nModifyPhoneModelReq: NModifyPhoneModelReq)
    }
}