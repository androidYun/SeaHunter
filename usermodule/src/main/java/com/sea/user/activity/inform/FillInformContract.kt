package com.sea.user.activity.inform

import com.xhs.baselibrary.base.IBaseView
import com.xhs.prison.model.NFillInformReq

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
interface FillInformContract {

    interface IFillInformView : IBaseView {

        fun loadFillInformSuccess()

        fun loadFillInformFail(throwable: Throwable)
    }

    interface IFillInformPresenter {
        fun loadFillInform(nFillInformReq: NFillInformReq)
    }
}