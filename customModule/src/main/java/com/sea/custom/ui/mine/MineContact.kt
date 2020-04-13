package com.sea.custom.ui.mine

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface MineContact {

    interface IMineView : IBaseView {

        fun loadMineSuccess(content: Any)

        fun loadMineFail(throwable: Throwable)

    }

    interface IMinePresenter {
        fun loadMine(nMineModelReq: NMineModelReq)
    }
}