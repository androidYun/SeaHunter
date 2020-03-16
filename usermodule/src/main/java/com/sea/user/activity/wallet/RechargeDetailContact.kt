package com.sea.user.activity.wallet

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface RechargeDetailContact {

    interface IRechargeDetailView : IBaseView {

        fun loadRechargeDetailSuccess(nRechargeDetailListItemList: List<RechargeDetailListItem>, totalCount: Int = 0)

        fun loadRechargeDetailFail(throwable: Throwable)

    }

    interface IRechargeDetailPresenter {
        fun loadRechargeDetail(nRechargeDetailReq: NRechargeDetailReq)
    }
}