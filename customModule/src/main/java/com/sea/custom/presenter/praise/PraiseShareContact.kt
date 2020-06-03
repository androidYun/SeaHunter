package com.sea.custom.presenter.praise

import com.xhs.baselibrary.base.IBaseView

interface PraiseShareContact {

    interface IPraiseShareView : IBaseView {

        fun loadPraiseShareSuccess(content: Any)

        fun loadPraiseShareFail(throwable: Throwable)

    }

    interface IPraiseSharePresenter {
        fun loadPraiseShare(mNPraiseShareModelReq: NPraiseShareModelReq)
    }
}