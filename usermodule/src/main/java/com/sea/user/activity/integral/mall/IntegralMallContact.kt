package com.sea.user.activity.integral.mall

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface IntegralMallContact {

    interface IIntegralMallView : IBaseView {

        fun loadIntegralMallSuccess(mList: List<IntegralMallItem>)

        fun loadIntegralMallFail(throwable: Throwable)

    }

    interface IIntegralMallPresenter {
        fun loadIntegralMall(nIntegralMallModelReq: NIntegralMallModelReq)
    }
}