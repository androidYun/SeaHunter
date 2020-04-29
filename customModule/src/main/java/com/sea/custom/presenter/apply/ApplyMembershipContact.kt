package com.sea.custom.presenter.apply

import com.xhs.baselibrary.base.IBaseView

interface ApplyMembershipContact {

    interface IApplyMembershipView : IBaseView {

        fun loadApplyMembershipSuccess()

        fun loadApplyMembershipFail(throwable: Throwable)

    }

    interface IApplyMembershipPresenter {
        fun loadApplyMembership(nApplyMembershipReq: NApplyMembershipReq)
    }
}