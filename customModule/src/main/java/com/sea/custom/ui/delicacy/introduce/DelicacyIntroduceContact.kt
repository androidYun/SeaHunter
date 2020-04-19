package com.sea.custom.ui.delicacy.introduce

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface DelicacyIntroduceContact {

    interface IDelicacyIntroduceView : IBaseView {

        fun loadDelicacyIntroduceSuccess(mList: List<DelicacyIntroduceItem>, totalCount: Int)

        fun loadDelicacyIntroduceFail(throwable: Throwable)

    }

    interface IDelicacyIntroducePresenter {
        fun loadDelicacyIntroduce(nDelicacyIntroduceModelReq: NDelicacyIntroduceModelReq)
    }
}