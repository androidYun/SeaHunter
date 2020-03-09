package com.sea.user.activity.feedback.complaint

class NComplaintFeedbackResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NComplaintFeedbackModelReq