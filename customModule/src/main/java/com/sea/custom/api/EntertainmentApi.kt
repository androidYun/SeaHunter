package com.sea.custom.api

import com.sea.custom.ui.entertainment.NEntertainmentModelReq
import com.sea.custom.ui.entertainment.NEntertainmentResponse
import com.sea.custom.ui.entertainment.list.NEntertainmentListModelReq
import com.sea.custom.ui.entertainment.list.NEntertainmentListModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface EntertainmentApi {

    @POST("getdata.ashx")
    fun loadEntertainment(@Body nEntertainmentModelReq: NEntertainmentModelReq): Observable<NEntertainmentResponse>
    @POST("getdata.ashx")
    fun loadEntertainmentList(@Body nEntertainmentListModelReq: NEntertainmentListModelReq): Observable<NEntertainmentListModelResponse>
}