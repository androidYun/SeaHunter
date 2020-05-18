package com.sea.publicmodule.api

import com.sea.publicmodule.activity.model.BaseEntry
import com.sea.publicmodule.activity.search.NAddSearchMallModelReq
import com.sea.publicmodule.activity.search.NSearchMallModelReq
import com.sea.publicmodule.activity.search.NSearchMallModelResponse
import com.sea.publicmodule.activity.search.NSearchModelReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {
    @POST("getdata.ashx")
    fun loadDeleteSearch(@Body nSearchMallModelReq: NSearchMallModelReq): Observable<BaseEntry>

    @POST("getdata.ashx")
    fun loadAddSearch(@Body nAddSearchMallModelReq: NAddSearchMallModelReq): Observable<BaseEntry>

    @POST("getdata.ashx")
    fun loadSearchList(@Body nSearchModelReq: NSearchModelReq): Observable<NSearchMallModelResponse>
}