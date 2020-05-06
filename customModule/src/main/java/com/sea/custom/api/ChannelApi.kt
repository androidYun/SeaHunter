package com.sea.custom.api

import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.presenter.channel.NChannelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ChannelApi {
    @POST("getdata.ashx")
    fun loadChannel(@Body nChannelModelReq: NChannelModelReq): Observable<NChannelResponse>
}