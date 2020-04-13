package com.sea.custom.ui.make

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface DelicacyMakeContact {

    interface IDelicacyMakeView : IBaseView {

        fun loadDelicacyMakeSuccess(content: Any)

        fun loadDelicacyMakeFail(throwable: Throwable)

    }

    interface IDelicacyMakePresenter {
        fun loadDelicacyMake(nDelicacyMakeModelReq: NDelicacyMakeModelReq)
    }
}