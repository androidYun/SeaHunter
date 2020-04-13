package com.sea.custom.ui.entertainment

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface EntertainmentContact {

    interface IEntertainmentView : IBaseView {

        fun loadEntertainmentSuccess(content: Any)

        fun loadEntertainmentFail(throwable: Throwable)

    }

    interface IEntertainmentPresenter {
        fun loadEntertainment(nEntertainmentModelReq: NEntertainmentModelReq)
    }
}