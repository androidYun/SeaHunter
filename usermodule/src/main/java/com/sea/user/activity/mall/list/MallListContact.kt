package com.sea.user.activity.mall.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface MallListContact {

    interface IMallListView : IBaseView {

        fun loadMallListSuccess(mList: List<MallListItem>, totalCount: Int)

        fun loadMallListFail(throwable: Throwable)

    }

    interface IMallListPresenter {
        fun loadMallList(nMallListModelReq: NMallListModelReq)
    }
}