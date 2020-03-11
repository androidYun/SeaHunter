package com.sea.user.activity.mall.car

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ShopCarContact {

    interface IShopCarView : IBaseView {

        fun loadShopCarSuccess(mList: List<ShopCarItem>)

        fun loadShopCarFail(throwable: Throwable)

    }

    interface IShopCarPresenter {
        fun loadShopCar(nShopCarModelReq: NShopCarModelReq)
    }
}