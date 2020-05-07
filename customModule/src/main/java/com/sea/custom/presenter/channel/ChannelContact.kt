package com.sea.custom.presenter.channel

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface ChannelContact {

    interface IChannelView : IBaseView {

        fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int)

        fun loadChannelFail(throwable: Throwable)

    }

    interface IChannelPresenter {
        fun loadChannel(nChannelModelReq: NChannelModelReq)
    }
}