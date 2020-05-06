package com.sea.custom.ui.member

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface MemberCustomContact {

    interface IMemberCustomView : IBaseView {

        fun loadMemberCustomSuccess(mList: List<MemberCustomItem>, totalCount: Int)

        fun loadMemberCustomFail(throwable: Throwable)

    }

    interface IMemberCustomPresenter {
        fun loadMemberCustom(nMemberCustomModelReq: NMemberCustomModelReq)
    }
}