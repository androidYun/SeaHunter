package com.sea.custom.api

import com.sea.custom.ui.mine.NMineResponse
import io.reactivex.Observable
import retrofit2.http.POST

interface MineApi {


    @POST("getdata.ashx")
    fun loadMine(): Observable<NMineResponse>
}