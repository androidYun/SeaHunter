package com.sea.user.presenter.sea.order

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface PlaceOrderContact {

    interface IPlaceOrderView : IBaseView {

        fun loadPlaceOrderSuccess(order: String)

        fun loadPlaceOrderFail(throwable: Throwable)

    }

    interface IPlaceOrderPresenter {
        fun loadPlaceOrder(nPlaceOrderModelReq: NPlaceOrderModelReq)
    }
}