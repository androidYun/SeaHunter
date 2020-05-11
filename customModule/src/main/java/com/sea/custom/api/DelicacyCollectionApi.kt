package com.sea.custom.api

import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface DelicacyCollectionApi {
    @POST("getdata.ashx")
    fun loadDelicacyCollection(@Body nDelicacyCollectionModelReq: NDelicacyCollectionModelReq): Observable<NDelicacyCollectionResponse>
    @POST("getdata.ashx")
    fun loadCancelDelicacyCollection(@Body nDelicacyCollectionModelReq: NCancelDelicacyCollectionModelReq): Observable<NDelicacyCollectionResponse>
}