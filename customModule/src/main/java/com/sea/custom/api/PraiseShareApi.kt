package com.sea.custom.api

import com.sea.custom.presenter.apply.NApplyMembershipReq
import com.sea.custom.presenter.apply.NApplyMembershipResponse
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.NPraiseShareResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface PraiseShareApi {
    @POST("getdata.ashx")
    fun loadPraiseShare(@Body nPraiseShareModelReq: NPraiseShareModelReq): Observable<NPraiseShareResponse>
}