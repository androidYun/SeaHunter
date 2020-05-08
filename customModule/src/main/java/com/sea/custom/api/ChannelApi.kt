package com.sea.custom.api

import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.presenter.channel.NChannelResponse
import com.sea.custom.presenter.channel.detail.NChannelDetailModelReq
import com.sea.custom.presenter.channel.detail.NChannelDetailResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ChannelApi {
    @POST("getdata.ashx")
    fun loadChannel(@Body nChannelModelReq: NChannelModelReq): Observable<NChannelResponse>


    @POST("getdata.ashx")
    fun loadChannelDetail(@Body nChannelDetailModelReq: NChannelDetailModelReq): Observable<NChannelDetailResponse>
}