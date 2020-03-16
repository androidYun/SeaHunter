package com.sea.user.presenter.store

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface StoreListContact {

    interface IStoreListView : IBaseView {

        fun loadStoreListSuccess(mList: List<NStoreListItemModel>)

        fun loadStoreListFail(throwable: Throwable)

    }

    interface IStoreListPresenter {
        fun loadStoreList(nStoreListModelReq: NStoreListModelReq)
    }
}