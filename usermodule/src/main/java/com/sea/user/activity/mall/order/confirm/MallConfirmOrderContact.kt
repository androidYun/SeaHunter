package com.sea.user.activity.mall

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface MallConfirmOrderContact {

    interface IMallConfirmOrderView : IBaseView {

        fun loadMallConfirmOrderSuccess(mList: List<MallConfirmOrderItem>)

        fun loadMallConfirmOrderFail(throwable: Throwable)

    }

    interface IMallConfirmOrderPresenter {
        fun loadMallConfirmOrder(nMallConfirmOrderModelReq: NMallConfirmOrderModelReq)
    }
}