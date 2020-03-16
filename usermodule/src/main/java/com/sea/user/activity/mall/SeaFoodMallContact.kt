package com.sea.user.activity.mall

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.NMallListModelReq

interface SeaFoodMallContact {

    interface ISeaFoodMallView : IBaseView {

        fun loadSeaCategoryListSuccess(seaCategoryItemModelList: List<SeaCategoryItemModel>)
        fun loadMallListRecommendSuccess(seaCategoryItemModelList: List<MallListItem>)
        fun loadMallListHotSuccess(seaCategoryItemModelList: List<MallListItem>)


        fun loadSeaFoodMallFail(throwable: Throwable)

    }

    interface ISeaFoodMallPresenter {

        fun loadMallListRecommend(nMallListModelReq: NMallListModelReq)

        fun loadMallListHot(nMallListModelReq: NMallListModelReq)

        fun loadSeaCategory()
    }
}