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
import com.sea.user.activity.mall.order.detail.NMallOrderDetailModelReq
import com.sea.user.activity.mall.order.detail.NMallOrderDetailResponse
import com.sea.user.activity.mall.order.list.NShopOrderListModelReq
import com.sea.user.activity.mall.order.list.NShopOrderListModelResponse
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

    @POST("getdata.ashx")
    fun loadShopOrder(@Body nShopOrderModelReq: NShopOrderModelReq): Observable<NShopOrderModelResponse>

    @POST("getdata.ashx")
    fun loadSelectStore(@Body nSelectStoreModelReq: NSelectStoreModelReq): Observable<NSelectStoreModelResponse>

    @POST("getdata.ashx")
    fun loadSearchStore(@Body nSearchStoreModelReq: NSearchStoreModelReq): Observable<NSearchStoreModelResponse>

    @POST("getdata.ashx")
    fun loadMallList(@Body nMallListModelReq: NMallListModelReq): Observable<NMallListModelResponse>

    @POST("getdata.ashx")
    fun loadSeaFoodMall(@Body nSeaFoodMallModelReq: NSeaFoodMallModelReq): Observable<NSeaFoodMallResponse>

    @POST("getdata.ashx")
    fun loadShopDetail(@Body nShopDetailModelReq: NShopDetailModelReq): Observable<NShopDetailResponse>

    @POST("getdata.ashx")
    fun loadShopCar(@Body nShopCarModelReq: NShopCarModelReq): Observable<NShopCarModelResponse>

    @POST("getdata.ashx")
    fun loadMallConfirmOrder(@Body nMallConfirmOrderModelReq: NMallConfirmOrderModelReq): Observable<NMallConfirmOrderModelResponse>

    @POST("getdata.ashx")
    fun loadShopOrderList(@Body nShopOrderListModelReq: NShopOrderListModelReq): Observable<NShopOrderListModelResponse>

    @POST("getdata.ashx")
    fun loadMallOrderDetail(@Body nMallOrderDetailModelReq: NMallOrderDetailModelReq): Observable<NMallOrderDetailResponse>
}