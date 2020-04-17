package com.sea.custom.ui.custom

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface MineCustomContact {

    interface IMineCustomView : IBaseView {

        fun loadMineCustomSuccess(mList: List<MineCustomItem>, totalCount: Int)

        fun loadMineCustomFail(throwable: Throwable)

    }

    interface IMineCustomPresenter {
        fun loadMineCustom(nMineCustomModelReq: NMineCustomModelReq)
    }
}