package com.sea.custom.ui.membership

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface MembershipModeContact {

    interface IMembershipModeView : IBaseView {

        fun loadMembershipModeSuccess(mList: List<MembershipModeItem>, totalCount: Int)

        fun loadMembershipModeFail(throwable: Throwable)

    }

    interface IMembershipModePresenter {
        fun loadMembershipMode(nMembershipModeModelReq: NMembershipModeModelReq)
    }
}