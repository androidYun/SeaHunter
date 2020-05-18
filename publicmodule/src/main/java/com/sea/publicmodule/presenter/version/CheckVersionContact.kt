package com.sea.publicmodule.presenter.version

import com.xhs.baselibrary.base.IBaseView

interface CheckVersionContact {

    interface ICheckVersionView : IBaseView {

        fun loadCheckVersionSuccess(versionModel: VersionModel)

        fun loadCheckVersionFail(throwable: Throwable)

    }

    interface ICheckVersionPresenter {
        fun loadCheckVersion(nCheckVersionModelReq: NCheckVersionModelReq)
    }
}