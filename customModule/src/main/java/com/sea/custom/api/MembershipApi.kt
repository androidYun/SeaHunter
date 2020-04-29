package com.sea.custom.api

import com.sea.custom.presenter.apply.NApplyMembershipReq
import com.sea.custom.presenter.apply.NApplyMembershipResponse
import com.sea.custom.ui.membership.NMembershipModeModelReq
import com.sea.custom.ui.membership.NMembershipModeModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface MembershipApi {
    @POST("getdata.ashx")
    fun loadMembershipMode(@Body nMembershipModeModelReq: NMembershipModeModelReq): Observable<NMembershipModeModelResponse>


    @POST("getdata.ashx")
    fun loadApplyMembership(@Body nApplyMembershipReq: NApplyMembershipReq): Observable<NApplyMembershipResponse>
}