package com.sea.custom.api

import com.sea.custom.ui.collection.NCollectionModelReq
import com.sea.custom.ui.collection.NCollectionModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CollectionApi {

    @POST("getdata.ashx")
    fun loadCollection(@Body nCollectionModelReq: NCollectionModelReq): Observable<NCollectionModelResponse>

}