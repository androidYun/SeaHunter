package com.sea.custom.ui.club.match

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface ClubMatchContact {

    interface IClubMatchView : IBaseView {

        fun loadClubMatchSuccess(mList: List<ClubMatchItem>, totalCount: Int)

        fun loadClubMatchFail(throwable: Throwable)

    }

    interface IClubMatchPresenter {
        fun loadClubMatch(nClubMatchModelReq: NClubMatchModelReq)
    }
}