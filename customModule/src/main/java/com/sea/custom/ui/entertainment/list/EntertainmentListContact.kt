package com.sea.custom.ui.entertainment.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface EntertainmentListContact {

    interface IEntertainmentListView : IBaseView {

        fun loadEntertainmentListSuccess(mList: List<EntertainmentListItem>, totalCount: Int)

        fun loadEntertainmentListFail(throwable: Throwable)

    }

    interface IEntertainmentListPresenter {
        fun loadEntertainmentList(nEntertainmentListModelReq: NEntertainmentListModelReq)
    }
}