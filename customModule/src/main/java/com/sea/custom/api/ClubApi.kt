package com.sea.custom.api

import com.sea.custom.ui.club.NClubModelReq
import com.sea.custom.ui.club.NClubResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ClubApi {

    @POST("")
    fun loadClub(@Body mNClubModelReq: NClubModelReq): Observable<NClubResponse>
}