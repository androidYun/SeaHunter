package com.sea.custom.api

import com.sea.custom.ui.custom.NMineCustomModelReq
import com.sea.custom.ui.custom.NMineCustomModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomApi {

    @POST("getdata.ashx")
    fun loadMineCustom(@Body nMineCustomModelReq: NMineCustomModelReq): Observable<NMineCustomModelResponse>
}