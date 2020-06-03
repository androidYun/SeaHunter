package com.sea.custom.ui.collection

import com.sea.custom.presenter.channel.NChannelItem
import com.xhs.baselibrary.base.IBaseView

interface CollectionContact {

    interface ICollectionView : IBaseView {

        fun loadCollectionSuccess(mList: List<NChannelItem>, totalCount: Int)

        fun loadCollectionFail(throwable: Throwable)

    }

    interface ICollectionPresenter {
        fun loadCollection(nCollectionModelReq: NCollectionModelReq)
    }
}