package com.sea.user.activity.integral.detail

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface IntegralDetailContact {

    interface IIntegralDetailView : IBaseView {

        fun loadIntegralDetailSuccess(mList: List<IntegralDetailItem>, totalCount: Int)

        fun loadIntegralDetailFail(throwable: Throwable)

    }

    interface IIntegralDetailPresenter {
        fun loadIntegralDetail(nIntegralDetailModelReq: NIntegralDetailModelReq)
    }
}