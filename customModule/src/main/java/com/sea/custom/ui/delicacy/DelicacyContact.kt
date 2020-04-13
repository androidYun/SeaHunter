package com.sea.custom.ui.delicacy

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface DelicacyContact {

    interface IDelicacyView : IBaseView {

        fun loadDelicacySuccess(content: Any)

        fun loadDelicacyFail(throwable: Throwable)

    }

    interface IDelicacyPresenter {
        fun loadDelicacy(nDelicacyModelReq: NDelicacyModelReq)
    }
}