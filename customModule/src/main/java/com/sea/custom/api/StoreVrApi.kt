package com.sea.custom.api

import com.sea.custom.ui.delicacy.vr.list.NStoreVrModelReq
import com.sea.custom.ui.delicacy.vr.list.NStoreVrModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface StoreVrApi {

    @POST("getdata.ashx")
    fun loadStoreVrList(@Body nStoreVrModelReq: NStoreVrModelReq): Observable<NStoreVrModelResponse>
}