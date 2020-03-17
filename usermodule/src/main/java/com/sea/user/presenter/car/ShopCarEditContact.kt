package com.sea.user.presenter.car

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R
import com.sea.user.activity.mall.car.NEditShopCarModelReq

interface ShopCarEditContact {

    interface IShopCarEditView : IBaseView {

        fun loadShopCarEditSuccess()

        fun loadShopCarEditFail(throwable: Throwable)

    }

    interface IShopCarEditPresenter {
        fun loadShopCarEdit(nShopCarEditModelReq: NEditShopCarModelReq)
    }
}