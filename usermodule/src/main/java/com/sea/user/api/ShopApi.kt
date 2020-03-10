package com.sea.user.api

import com.sea.user.activity.mall.NSeaFoodMallModelReq
import com.sea.user.activity.mall.search.NSearchStoreModelReq
import com.sea.user.activity.mall.search.NSearchStoreModelResponse
import com.sea.user.activity.mall.select.NSelectStoreModelReq
import com.sea.user.activity.mall.select.NSelectStoreModelResponse
import com.sea.user.activity.shop.order.NShopOrderModelReq
import com.sea.user.activity.shop.order.NShopOrderModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopApi {

    @POST("api/v1")
    fun loadShopOrder(@Body nShopOrderModelReq: NShopOrderModelReq): Observable<NShopOrderModelResponse>

    @POST("api/v1")
    fun loadSelectStore(@Body nSelectStoreModelReq: NSelectStoreModelReq): Observable<NSelectStoreModelResponse>

    @POST("api/v1")
    fun loadSearchStore(@Body nSearchStoreModelReq: NSearchStoreModelReq): Observable<NSearchStoreModelResponse>
}