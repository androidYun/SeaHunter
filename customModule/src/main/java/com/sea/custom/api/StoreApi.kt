package com.sea.custom.api

import com.sea.custom.ui.store.NStoreListModelReq
import com.sea.custom.ui.store.NStoreListModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface StoreApi {

    @POST("getdata.ashx")
    fun loadStoreList(@Body nStoreListModelReq: NStoreListModelReq): Observable<NStoreListModelResponse>
}