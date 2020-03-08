package com.sea.user.api

import com.sea.user.activity.shop.order.NShopOrderModelReq
import com.sea.user.activity.shop.order.NShopOrderModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopApi {

    @POST("api/v1")
    fun loadShopOrder(@Body nShopOrderModelReq: NShopOrderModelReq): Observable<NShopOrderModelResponse>
}