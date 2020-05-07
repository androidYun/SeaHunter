package com.sea.custom.ui.collection

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface CollectionContact {

    interface ICollectionView : IBaseView {

        fun loadCollectionSuccess(mList: List<CollectionItem>, totalCount: Int)

        fun loadCollectionFail(throwable: Throwable)

    }

    interface ICollectionPresenter {
        fun loadCollection(nCollectionModelReq: NCollectionModelReq)
    }
}