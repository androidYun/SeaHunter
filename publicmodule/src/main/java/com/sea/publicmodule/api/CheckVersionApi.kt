package com.sea.publicmodule.api

import com.sea.publicmodule.presenter.version.NCheckVersionModelReq
import com.sea.publicmodule.presenter.version.NCheckVersionResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckVersionApi {


    @POST("getdata.ashx")
    fun loadCheckVersion(@Body nCheckVersionModelReq: NCheckVersionModelReq = NCheckVersionModelReq()): Observable<NCheckVersionResponse>

}