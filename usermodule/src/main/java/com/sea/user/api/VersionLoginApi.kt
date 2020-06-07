package com.sea.user.api

import com.sea.user.activity.login.NLoginResponse
import com.sea.user.activity.login.version.NVersionLoginModelReq
import com.sea.user.activity.model.BaseEntry
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface VersionLoginApi {

    @POST("getdata.ashx")
    fun loadVersionLogin(@Body nVersionLoginModelReq: NVersionLoginModelReq): Observable<NLoginResponse>
}