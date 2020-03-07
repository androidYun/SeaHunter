package com.sea.user.activity.order

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ShopOrderContact {

    interface IShopOrderView : IBaseView {

        fun loadShopOrderSuccess(mList: List<ShopOrderItem>, totalCount: Int)

        fun loadShopOrderFail(throwable: Throwable)

    }

    interface IShopOrderPresenter {
        fun loadShopOrder(nShopOrderModelReq: NShopOrderModelReq)
    }
}