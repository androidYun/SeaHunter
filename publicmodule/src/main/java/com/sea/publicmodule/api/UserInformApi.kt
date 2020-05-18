package com.sea.publicmodule.api

import com.sea.publicmodule.presenter.user.NUserInformReq
import com.sea.publicmodule.presenter.user.NUserInformResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInformApi {


    @POST("getdata.ashx")
    fun loadUserInform(@Body nUserInformReq: NUserInformReq = NUserInformReq()): Observable<NUserInformResponse>

}