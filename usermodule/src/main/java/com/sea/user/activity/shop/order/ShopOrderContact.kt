package com.sea.user.activity.shop.order

import com.xhs.baselibrary.base.IBaseView

interface ShopOrderContact {

    interface IShopOrderView : IBaseView {

        fun loadShopOrderSuccess(mList: List<ShopOrderItem>, totalCount: Int)

        fun loadShopOrderFail(throwable: Throwable)

    }

    interface IShopOrderPresenter {
        fun loadShopOrder(nShopOrderModelReq: NShopOrderModelReq)
    }
}