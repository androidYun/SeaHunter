package com.sea.custom.ui.collection.make

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface DelicacyMakeContact {

    interface IDelicacyMakeView : IBaseView {

        fun loadDelicacyMakeSuccess(mList: List<DelicacyMakeItem>, totalCount: Int)

        fun loadDelicacyMakeFail(throwable: Throwable)

    }

    interface IDelicacyMakePresenter {
        fun loadDelicacyMake(nDelicacyMakeModelReq: NDelicacyMakeModelReq)
    }
}