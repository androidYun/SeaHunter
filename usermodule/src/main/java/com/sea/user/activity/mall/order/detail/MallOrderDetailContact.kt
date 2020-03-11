package com.sea.user.activity.mall.order.detail

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface MallOrderDetailContact {

    interface IMallOrderDetailView : IBaseView {

        fun loadMallOrderDetailSuccess(content: Any)

        fun loadMallOrderDetailFail(throwable: Throwable)

    }

    interface IMallOrderDetailPresenter {
        fun loadMallOrderDetail(nMallOrderDetailModelReq: NMallOrderDetailModelReq)
    }
}