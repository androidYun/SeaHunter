package com.sea.user.api

import com.sea.user.activity.integral.detail.NIntegralDetailModelReq
import com.sea.user.activity.integral.detail.NIntegralDetailModelResponse
import com.sea.user.activity.integral.mall.NIntegralMallModelReq
import com.sea.user.activity.integral.mall.NIntegralMallModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface IntegralApi {

    @POST("getdata.ashx")
    fun loadIntegralMall(@Body nIntegralMallModelReq: NIntegralMallModelReq): Observable<NIntegralMallModelResponse>

    @POST("getdata.ashx")
    fun loadIntegralMall(@Body nIntegralDetailModelReq: NIntegralDetailModelReq): Observable<NIntegralDetailModelResponse>





}