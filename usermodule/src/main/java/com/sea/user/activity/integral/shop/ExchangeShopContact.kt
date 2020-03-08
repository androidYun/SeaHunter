package com.sea.user.activity.integral.shop

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ExchangeShopContact {

    interface IExchangeShopView : IBaseView {

        fun loadExchangeShopSuccess(content: Any)

        fun loadExchangeShopFail(throwable: Throwable)

    }

    interface IExchangeShopPresenter {
        fun loadExchangeShop(nExchangeShopModelReq: NExchangeShopModelReq)
    }
}