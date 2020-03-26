package com.sea.user.api

import com.sea.user.activity.login.NLoginModelReq
import com.sea.user.activity.login.NLoginResponse
import com.sea.user.activity.model.BaseEntry
import com.sea.user.activity.phone.NModifyPhoneModelReq
import com.sea.user.activity.register.NRegisterModelReq
import com.sea.user.activity.register.NRegisterModelResponse
import com.sea.user.presenter.user.NUserInformReq
import com.sea.user.presenter.user.NUserInformResponse
import com.xhs.prison.model.NFillInformReq
import com.xhs.prison.model.NForgetPasswordModelReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInformApi {
    @POST("getdata.ashx")
    fun loadLogin(@Body nLoginModelReq: NLoginModelReq): Observable<NLoginResponse>

    @POST("getdata.ashx")
    fun loadRegister(@Body nRegisterReq: NRegisterModelReq): Observable<NRegisterModelResponse>


    @GET("getdata.ashx")
    fun loadVersionCode(@Query("phoneNumber") phoneNumber: String): Observable<BaseEntry>

    @GET("getdata.ashx")
    fun loadForgetPassword(@Body nForgetPasswordModelReq: NForgetPasswordModelReq): Observable<BaseEntry>

    @POST("getdata.ashx")
    fun loadFillInform(@Body nFillInformReq: NFillInformReq): Observable<BaseEntry>


    @POST("getdata.ashx")
    fun loadUserInform(@Body nUserInformReq: NUserInformReq = NUserInformReq()): Observable<NUserInformResponse>

    @POST("getdata.ashx")
    fun loadModifyPhone(@Body nModifyPhoneModelReq: NModifyPhoneModelReq): Observable<BaseEntry>
}