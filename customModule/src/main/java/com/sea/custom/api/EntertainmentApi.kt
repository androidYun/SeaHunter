package com.sea.custom.api

import com.sea.custom.ui.entertainment.NEntertainmentModelReq
import com.sea.custom.ui.entertainment.NEntertainmentResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface EntertainmentApi {

    @POST
    fun loadEntertainment(@Body nEntertainmentModelReq: NEntertainmentModelReq): Observable<NEntertainmentResponse>
}