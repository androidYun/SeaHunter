package com.sea.user.api

import com.sea.user.activity.mall.NSeaFoodMallModelReq
import com.sea.user.activity.mall.NSeaFoodMallResponse
import com.sea.user.activity.mall.car.NDeleteShopCarModelReq
import com.sea.user.activity.mall.car.NEditShopCarModelReq
import com.sea.user.activity.mall.car.NShopCarModelReq
import com.sea.user.activity.mall.car.NShopCarModelResponse
import com.sea.user.activity.mall.detail.NShopDetailModelReq
import com.sea.user.activity.mall.detail.NShopDetailResponse
import com.sea.user.activity.mall.order.list.NShopOrderListModelReq
import com.sea.user.activity.mall.order.list.NShopOrderListModelResponse
import com.sea.user.activity.model.BaseEntry
import com.sea.user.activity.shop.order.NShopOrderModelReq
import com.sea.user.activity.shop.order.NShopOrderModelResponse
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.sea.user.presenter.sea.mall.NMallListModelResponse
import com.sea.user.presenter.sea.order.NPlaceOrderModelReq
import com.sea.user.presenter.sea.order.NPlaceOrderResponse
import com.sea.user.presenter.sea.order.cancel.NCancelOrderModelReq
import com.sea.user.presenter.store.NStoreListModelReq
import com.sea.user.presenter.store.NStoreListResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopApi {

    @POST("getdata.ashx")
    fun loadShopOrder(@Body nShopOrderModelReq: NShopOrderModelReq): Observable<NShopOrderModelResponse>


    @POST("getdata.ashx")
    fun loadCancelOrder(@Body nCancelOrderModelReq: NCancelOrderModelReq): Observable<BaseEntry>




    @POST("getdata.ashx")
    fun loadMallList(@Body nMallListModelReq: NMallListModelReq): Observable<NMallListModelResponse>

    @POST("getdata.ashx")
    fun loadSeaCategory(@Body nSeaFoodMallModelReq: NSeaFoodMallModelReq): Observable<NSeaFoodMallResponse>

    @POST("getdata.ashx")
    fun loadShopDetail(@Body nShopDetailModelReq: NShopDetailModelReq): Observable<NShopDetailResponse>

    @POST("getdata.ashx")
    fun loadShopCar(@Body nShopCarModelReq: NShopCarModelReq): Observable<NShopCarModelResponse>


    @POST("getdata.ashx")
    fun loadDeleteShopCar(@Body nDeleteShopCarModelReq: NDeleteShopCarModelReq): Observable<BaseEntry>

    @POST("getdata.ashx")
    fun loadEditShopCar(@Body nEditShopCarModelReq: NEditShopCarModelReq): Observable<BaseEntry>

    @POST("getdata.ashx")
    fun loadShopOrderList(@Body nShopOrderListModelReq: NShopOrderListModelReq): Observable<NShopOrderListModelResponse>


    @POST("getdata.ashx")
    fun loadStoreList(@Body nStoreListModelReq: NStoreListModelReq): Observable<NStoreListResponse>

    @POST("getdata.ashx")
    fun loadPlaceOrder(@Body nPlaceOrderModelReq: NPlaceOrderModelReq): Observable<NPlaceOrderResponse>
}