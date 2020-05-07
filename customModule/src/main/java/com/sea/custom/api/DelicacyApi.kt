package com.sea.custom.api

import com.sea.custom.ui.delicacy.NDelicacyModelReq
import com.sea.custom.ui.delicacy.NDelicacyResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface DelicacyApi {

    @POST("getdata.ashx")
    fun loadDelicacy(@Body nDelicacyModelReq: NDelicacyModelReq): Observable<NDelicacyResponse>


}