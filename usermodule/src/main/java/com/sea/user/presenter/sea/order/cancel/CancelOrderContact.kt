package com.sea.user.presenter.sea.order.cancel

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface CancelOrderContact {

    interface ICancelOrderView : IBaseView {

        fun loadCancelOrderSuccess()

        fun loadCancelOrderFail(throwable: Throwable)

    }

    interface ICancelOrderPresenter {
        fun loadCancelOrder(nCancelOrderModelReq: NCancelOrderModelReq)
    }
}