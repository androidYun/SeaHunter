package com.sea.custom.api

import com.sea.custom.ui.make.NDelicacyMakeModelReq
import com.sea.custom.ui.make.NDelicacyMakeResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface DelicacyMakeApi {

    @POST("getdata.ashx")
    fun loadDelicacyMake(@Body nDelicacyMakeModelReq: NDelicacyMakeModelReq): Observable<NDelicacyMakeResponse>
}