package com.sea.custom.api

import com.sea.custom.ui.delicacy.introduce.NDelicacyIntroduceModelReq
import com.sea.custom.ui.delicacy.introduce.NDelicacyIntroduceModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface DelicacyIntroduceApi {

    @POST("getdata.ashx")
    fun loadDelicacyIntroduce(@Body nDelicacyIntroduceModelReq: NDelicacyIntroduceModelReq): Observable<NDelicacyIntroduceModelResponse>
}