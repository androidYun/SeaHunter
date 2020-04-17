package com.sea.custom.api

import com.sea.custom.ui.club.NClubModelReq
import com.sea.custom.ui.club.NClubResponse
import com.sea.custom.ui.club.match.NClubMatchModelReq
import com.sea.custom.ui.club.match.NClubMatchModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ClubApi {

    @POST("getdata.ashx")
    fun loadClub(@Body mNClubModelReq: NClubModelReq): Observable<NClubResponse>

    @POST("getdata.ashx")
    fun loadClubMatch(@Body nClubMatchModelReq: NClubMatchModelReq): Observable<NClubMatchModelResponse>
}