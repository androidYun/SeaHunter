package com.sea.user.activity.integral.mall.detail

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface IntegralShopDetailContact {

    interface IIntegralShopDetailView : IBaseView {

        fun loadIntegralShopDetailSuccess(content: Any)

        fun loadIntegralShopDetailFail(throwable: Throwable)

    }

    interface IIntegralShopDetailPresenter {
        fun loadIntegralShopDetail(nIntegralShopDetailModelReq: NIntegralShopDetailModelReq)
    }
}