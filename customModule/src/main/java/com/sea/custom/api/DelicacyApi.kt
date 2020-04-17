package com.sea.custom.api

import com.sea.custom.ui.collection.introduce.NDelicacyIntroduceModelReq
import com.sea.custom.ui.collection.introduce.NDelicacyIntroduceModelResponse
import com.sea.custom.ui.collection.make.NDelicacyMakeModelReq
import com.sea.custom.ui.collection.make.NDelicacyMakeModelResponse
import com.sea.custom.ui.delicacy.NDelicacyModelReq
import com.sea.custom.ui.delicacy.NDelicacyResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface DelicacyApi {

    @POST("getdata.ashx")
    fun loadDelicacy(@Body nDelicacyModelReq: NDelicacyModelReq): Observable<NDelicacyResponse>

    @POST("getdata.ashx")
    fun loadDelicacyIntroduce(@Body nDelicacyIntroduceModelReq: NDelicacyIntroduceModelReq): Observable<NDelicacyIntroduceModelResponse>

    @POST("getdata.ashx")
    fun loadDelicacyMake(@Body nDelicacyMakeModelReq: NDelicacyMakeModelReq): Observable<NDelicacyMakeModelResponse>
}