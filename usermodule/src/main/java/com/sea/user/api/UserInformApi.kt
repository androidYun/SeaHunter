package com.sea.user.api

import com.sea.user.activity.center.NUserCenterModelReq
import com.sea.user.activity.center.NUserCenterResponse
import com.sea.user.activity.login.NLoginModelReq
import com.sea.user.activity.login.NLoginResponse
import com.sea.user.activity.model.BaseEntry
import com.xhs.prison.model.NFillInformReq
import com.xhs.prison.model.NForgetPasswordModelReq
import com.xhs.prison.model.NRegisterModelReq
import com.xhs.prison.model.NRegisterModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInformApi {
    @POST("/")
    fun loadLogin(@Body nLoginModelReq: NLoginModelReq): Observable<NLoginResponse>

    @POST("/")
    fun loadRegister(@Body nRegisterReq: NRegisterModelReq): Observable<NRegisterModelResponse>


    @GET("/")
    fun loadVersionCode(@Query("phoneNumber") phoneNumber: String): Observable<BaseEntry>

    @GET("/")
    fun loadForgetPassword(@Body nForgetPasswordModelReq: NForgetPasswordModelReq): Observable<BaseEntry>

    @POST("/")
    fun loadFillInform(@Body nFillInformReq: NFillInformReq): Observable<BaseEntry>

    @POST("/")
    fun loadUserInform(@Body nUserCenterModelReq: NUserCenterModelReq): Observable<NUserCenterResponse>
}