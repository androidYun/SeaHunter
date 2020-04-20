package com.sea.custom.ui.club

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface ClubContact {

    interface IClubView : IBaseView {

        fun loadClubSuccess(mList: List<NClubActivityItem>)

        fun loadClubFail(throwable: Throwable)

    }

    interface IClubPresenter {
        fun loadClub(nClubModelReq: NClubModelReq)
    }
}