package com.sea.user.api

import com.sea.user.activity.mall.NSeaFoodMallModelReq
import com.sea.user.activity.mall.NSeaFoodMallResponse
import com.sea.user.activity.mall.car.NShopCarModelReq
import com.sea.user.activity.mall.car.NShopCarModelResponse
import com.sea.user.activity.mall.detail.NShopDetailModelReq
import com.sea.user.activity.mall.detail.NShopDetailResponse
import com.sea.user.activity.mall.list.NMallListModelReq
import com.sea.user.activity.mall.list.NMallListModelResponse
import com.sea.user.activity.mall.order.confirm.NMallConfirmOrderModelReq
import com.sea.user.activity.mall.order.confirm.NMallConfirmOrderModelResponse
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

    @POST("api/v1")
    fun loadMallList(@Body nMallListModelReq: NMallListModelReq): Observable<NMallListModelResponse>

    @POST("api/v1")
    fun loadSeaFoodMall(@Body nSeaFoodMallModelReq: NSeaFoodMallModelReq): Observable<NSeaFoodMallResponse>

    @POST("api/v1")
    fun loadShopDetail(@Body nShopDetailModelReq: NShopDetailModelReq): Observable<NShopDetailResponse>

    @POST("api/v1")
    fun loadShopCar(@Body nShopCarModelReq: NShopCarModelReq): Observable<NShopCarModelResponse>

    @POST("api/v1")
    fun loadMallConfirmOrder(@Body nMallConfirmOrderModelReq: NMallConfirmOrderModelReq): Observable<NMallConfirmOrderModelResponse>
}