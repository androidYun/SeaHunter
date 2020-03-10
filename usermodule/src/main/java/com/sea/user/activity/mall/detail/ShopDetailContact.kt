package com.sea.user.activity.mall.detail

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ShopDetailContact {

    interface IShopDetailView : IBaseView {

        fun loadShopDetailSuccess(content: Any)

        fun loadShopDetailFail(throwable: Throwable)

    }

    interface IShopDetailPresenter {
        fun loadShopDetail(nShopDetailModelReq: NShopDetailModelReq)
    }
}