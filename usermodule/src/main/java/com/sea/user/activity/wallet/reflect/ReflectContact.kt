package com.sea.user.activity.wallet.reflect

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ReflectContact {

    interface IReflectView : IBaseView {

        fun loadReflectSuccess(content: Any)

        fun loadReflectFail(throwable: Throwable)

    }

    interface IReflectPresenter {
        fun loadReflect(nReflectModelReq: NReflectModelReq)
    }
}