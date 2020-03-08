package com.sea.user.api

import com.sea.user.activity.integral.detail.NIntegralDetailModelReq
import com.sea.user.activity.integral.detail.NIntegralDetailModelResponse
import com.sea.user.activity.integral.exchange.NExchangeListModelReq
import com.sea.user.activity.integral.exchange.NExchangeListModelResponse
import com.sea.user.activity.integral.mall.NIntegralMallModelReq
import com.sea.user.activity.integral.mall.NIntegralMallModelResponse
import com.sea.user.activity.integral.mall.detail.NIntegralShopDetailModelReq
import com.sea.user.activity.integral.mall.detail.NIntegralShopDetailResponse
import com.sea.user.activity.integral.shop.NExchangeShopModelReq
import com.sea.user.activity.integral.shop.NExchangeShopResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface IntegralApi {

    @POST("/")
    fun loadIntegralMall(@Body nIntegralMallModelReq: NIntegralMallModelReq): Observable<NIntegralMallModelResponse>

    @POST("/")
    fun loadIntegralMall(@Body nIntegralDetailModelReq: NIntegralDetailModelReq): Observable<NIntegralDetailModelResponse>


    @POST("/")
    fun loadExchangeList(@Body nExchangeListModelReq: NExchangeListModelReq): Observable<NExchangeListModelResponse>

    @POST("/")
    fun loadIntegralShopDetail(@Body nIntegralShopDetailModelReq: NIntegralShopDetailModelReq): Observable<NIntegralShopDetailResponse>

    @POST("/")
    fun loadExchangeShop(@Body nExchangeShopModelReq: NExchangeShopModelReq): Observable<NExchangeShopResponse>


}