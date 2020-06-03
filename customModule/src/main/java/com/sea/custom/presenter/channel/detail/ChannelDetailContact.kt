package com.sea.custom.presenter.channel.detail

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R
import com.sea.custom.presenter.channel.NChannelItem

interface ChannelDetailContact {

    interface IChannelDetailView : IBaseView {

        fun loadChannelDetailSuccess(content: NChannelItem)

        fun loadChannelDetailFail(throwable: Throwable)

    }

    interface IChannelDetailPresenter {
        fun loadChannelDetail(nChannelDetailModelReq: NChannelDetailModelReq)
    }
}