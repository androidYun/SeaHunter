package com.sea.user.presenter.version

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface VersionCodeContact {

    interface IVersionCodeView : IBaseView {

        fun loadVersionCodeSuccess(versionCode: String)

        fun loadVersionCodeFail(throwable: Throwable)

    }

    interface IVersionCodePresenter {
        fun loadVersionCode(nVersionCodeModelReq: NVersionCodeModelReq)
    }
}