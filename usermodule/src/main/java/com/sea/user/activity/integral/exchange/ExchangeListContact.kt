package com.sea.user.activity.integral.exchange

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ExchangeListContact {

    interface IExchangeListView : IBaseView {

        fun loadExchangeListSuccess(mList: List<ExchangeListItem>, totalCount: Int)

        fun loadExchangeListFail(throwable: Throwable)

    }

    interface IExchangeListPresenter {
        fun loadExchangeList(nExchangeListModelReq: NExchangeListModelReq)
    }
}