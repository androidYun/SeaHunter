package com.sea.user.activity.center

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface UserCenterContact {

    interface IUserCenterView : IBaseView {

        fun loadUserCenterSuccess(content: Any)

        fun loadUserCenterFail(throwable: Throwable)

    }

    interface IUserCenterPresenter {
        fun loadUserCenter(nUserCenterModelReq: NUserCenterModelReq)
    }
}