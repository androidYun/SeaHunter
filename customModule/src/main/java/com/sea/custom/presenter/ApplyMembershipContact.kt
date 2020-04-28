package com.sea.custom.presenter

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface ApplyMembershipContact {

    interface IApplyMembershipView : IBaseView {

        fun loadApplyMembershipSuccess()

        fun loadApplyMembershipFail(throwable: Throwable)

    }

    interface IApplyMembershipPresenter {
        fun loadApplyMembership(nApplyMembershipReq: NApplyMembershipReq)
    }
}