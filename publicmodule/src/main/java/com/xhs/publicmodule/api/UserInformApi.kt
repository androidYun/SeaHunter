package com.xhs.publicmodule.api

import com.xhs.publicmodule.presenter.user.NUserInformReq
import com.xhs.publicmodule.presenter.user.NUserInformResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInformApi {


    @POST("getdata.ashx")
    fun loadUserInform(@Body nUserInformReq: NUserInformReq = NUserInformReq()): Observable<NUserInformResponse>

}