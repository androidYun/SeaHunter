package com.sea.user.api

import com.sea.user.activity.feedback.complaint.NComplaintFeedbackModelReq
import com.sea.user.activity.feedback.complaint.NComplaintFeedbackResponse
import com.sea.user.activity.feedback.list.NFeedBackListModelReq
import com.sea.user.activity.feedback.list.NFeedBackListModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 09/03/2020.
 * description:
 */
interface FeedBackApi {
    @POST
    fun loadFeedBack(@Body nComplaintFeedbackModelReq: NComplaintFeedbackModelReq): Observable<NComplaintFeedbackResponse>


    @POST
    fun loadFeedBackList(@Body nFeedBackListModelReq: NFeedBackListModelReq): Observable<NFeedBackListModelResponse>
}