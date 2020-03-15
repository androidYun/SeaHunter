package com.sea.user.activity.register

import com.xhs.baselibrary.base.IBaseView

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
interface RegisterContract {

    interface IRegisterView : IBaseView {

        fun loadRegisterSuccess()

        fun loadRegisterFail(throwable: Throwable)

        fun loadVersionCodeSuccess()

        fun loadVersionCodeFail(throwable: Throwable)
    }

    interface IRegisterPresenter {
        fun loadRegister(nRegisterModelReq: NRegisterModelReq)

        fun loadVersionCode(phoneNumber: String)
    }
}