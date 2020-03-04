package com.xhs.baselibrary.base.example

import com.xhs.baselibrary.base.IBaseView

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 29/04/2019.
 * description:
 */
interface ExampleContract {
    interface ExampleView : IBaseView {
        fun loadLogin()
        fun loadLoginFail(throwable: Throwable)
    }

    interface ExamplePresenter {

        fun goLogin(phone: String, password: String)
    }
}
