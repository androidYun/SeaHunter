package com.sea.user.activity.mall

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface SeaFoodMallContact {

    interface ISeaFoodMallView : IBaseView {

        fun loadSeaFoodMallSuccess(content: Any)

        fun loadSeaFoodMallFail(throwable: Throwable)

    }

    interface ISeaFoodMallPresenter {
        fun loadSeaFoodMall(nSeaFoodMallModelReq: NSeaFoodMallModelReq)
    }
}