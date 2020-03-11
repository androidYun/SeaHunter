package com.sea.user.activity.mall.order.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ShopOrderListContact {

    interface IShopOrderListView : IBaseView {

        fun loadShopOrderListSuccess(mList: List<ShopOrderListItem>, totalCount: Int)

        fun loadShopOrderListFail(throwable: Throwable)

    }

    interface IShopOrderListPresenter {
        fun loadShopOrderList(nShopOrderListModelReq: NShopOrderListModelReq)
    }
}