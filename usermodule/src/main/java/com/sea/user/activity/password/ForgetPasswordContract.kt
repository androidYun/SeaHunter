package com.sea.user.activity.password

import com.xhs.baselibrary.base.IBaseView

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
interface ForgetPasswordContract {

    interface IForgetPasswordView : IBaseView {

        fun loadForgetPasswordSuccess()

        fun loadForgetPasswordFail(throwable: Throwable)
    }

    interface IForgetPasswordPresenter {
        fun loadForgetPassword(nForgetPasswordModelReq: NForgetPasswordModelReq)
    }
}