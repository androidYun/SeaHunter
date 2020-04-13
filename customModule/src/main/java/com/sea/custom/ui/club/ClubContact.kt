package com.sea.custom.ui.club

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface ClubContact {

    interface IClubView : IBaseView {

        fun loadClubSuccess(content: Any)

        fun loadClubFail(throwable: Throwable)

    }

    interface IClubPresenter {
        fun loadClub(nClubModelReq: NClubModelReq)
    }
}