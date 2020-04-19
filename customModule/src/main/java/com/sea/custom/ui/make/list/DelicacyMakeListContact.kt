package com.sea.custom.ui.make.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface DelicacyMakeListContact {

    interface IDelicacyMakeListView : IBaseView {

        fun loadDelicacyMakeListSuccess(mList: List<DelicacyMakeListItem>, totalCount: Int)

        fun loadDelicacyMakeListFail(throwable: Throwable)

    }

    interface IDelicacyMakeListPresenter {
        fun loadDelicacyMakeList(nDelicacyMakeListModelReq: NDelicacyMakeListModelReq)
    }
}