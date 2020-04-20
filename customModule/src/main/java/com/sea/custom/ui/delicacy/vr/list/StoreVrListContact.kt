package com.sea.custom.ui.delicacy.vr.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface StoreVrListContact {

    interface IStoreVrListView : IBaseView {

        fun loadStoreVrListSuccess(mList: List<StoreVrItem>, totalCount: Int)

        fun loadStoreVrListFail(throwable: Throwable)

    }

    interface IStoreVrListPresenter {
        fun loadStoreVrList(nStoreVrListModelReq: NStoreVrModelReq)
    }
}