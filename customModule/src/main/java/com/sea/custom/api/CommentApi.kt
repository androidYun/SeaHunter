package com.sea.custom.api

import com.sea.custom.presenter.comment.NCommentModelReq
import com.sea.custom.presenter.comment.NCommentResponse
import com.sea.custom.presenter.comment.NSubmitCommentModelReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentApi {
    @POST("getdata.ashx")
    fun loadComment(@Body nCommentModelReq: NCommentModelReq): Observable<NCommentResponse>

    @POST("getdata.ashx")
    fun submitComment(@Body nCommentModelReq: NSubmitCommentModelReq): Observable<NCommentResponse>
}