package com.sea.user.activity.mall.select

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface SelectStoreContact {

    interface ISelectStoreView : IBaseView {

        fun loadSelectStoreSuccess(mList: List<SelectStoreItem>, totalCount: Int)

        fun loadSelectStoreFail(throwable: Throwable)

    }

    interface ISelectStorePresenter {
        fun loadSelectStore(nSelectStoreModelReq: NSelectStoreModelReq)
    }
}