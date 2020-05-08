package com.sea.custom.presenter.channel.detail

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface ChannelDetailContact {

    interface IChannelDetailView : IBaseView {

        fun loadChannelDetailSuccess(content: ChannelDetail)

        fun loadChannelDetailFail(throwable: Throwable)

    }

    interface IChannelDetailPresenter {
        fun loadChannelDetail(nChannelDetailModelReq: NChannelDetailModelReq)
    }
}