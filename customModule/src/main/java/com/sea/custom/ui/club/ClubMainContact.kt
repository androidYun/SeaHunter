package com.sea.custom.ui.club

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq

interface ClubMainContact {

    interface IClubView : IBaseView {

        fun loadRecommendActivitySuccess(mList: List<NChannelItem>)
        fun loadDelicacySuccess(mList: List<NChannelItem>)
        fun loadDelicacyMakeSuccess(mList: List<NChannelItem>)
        fun loadEntertainmentListSuccess(mList: List<NChannelItem>)
        fun loadFail(throwable: Throwable)

    }

    interface IClubPresenter {
        fun loadRecommendActivityClub(nChannelModelReq: NChannelModelReq)
        fun loadDelicacy(nChannelModelReq: NChannelModelReq)
        fun loadDelicacyMake(nChannelModelReq: NChannelModelReq)
        fun loadEntertainmentList(nChannelModelReq: NChannelModelReq)
    }
}