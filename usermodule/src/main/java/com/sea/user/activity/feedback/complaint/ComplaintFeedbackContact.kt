package com.sea.user.activity.feedback.complaint

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface ComplaintFeedbackContact {

    interface IComplaintFeedbackView : IBaseView {

        fun loadComplaintFeedbackSuccess(content: Any)

        fun loadComplaintFeedbackFail(throwable: Throwable)

    }

    interface IComplaintFeedbackPresenter {
        fun loadComplaintFeedback(nComplaintFeedbackModelReq: NComplaintFeedbackModelReq)
    }
}