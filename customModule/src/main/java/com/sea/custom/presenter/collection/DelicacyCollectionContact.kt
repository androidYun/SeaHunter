package com.sea.custom.presenter.collection

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface DelicacyCollectionContact {

    interface IDelicacyCollectionView : IBaseView {

        fun loadDelicacyCollectionSuccess()

        fun loadDelicacyCollectionFail(throwable: Throwable)

    }

    interface IDelicacyCollectionPresenter {
        fun loadDelicacyCollection(nDelicacyCollectionModelReq: NDelicacyCollectionModelReq)

        fun cancelDelicacyCollection(nDelicacyCollectionModelReq: NCancelDelicacyCollectionModelReq)
    }
}