package com.sea.custom.ui.store

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface StoreListContact {

    interface IStoreListView : IBaseView {

        fun loadStoreListSuccess(mList: List<StoreListItem>, totalCount: Int)

        fun loadStoreListFail(throwable: Throwable)

    }

    interface IStoreListPresenter {
        fun loadStoreList(nStoreListModelReq: NStoreListModelReq)
    }
}