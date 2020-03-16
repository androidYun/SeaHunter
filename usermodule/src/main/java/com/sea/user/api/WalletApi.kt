package com.sea.user.api

import com.sea.user.activity.wallet.NRechargeDetailReq
import com.sea.user.activity.wallet.NRechargeDetailResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 06/03/2020.
 * description:
 */
interface WalletApi {

    @POST("/")
    fun loadRechargeDetail(@Body nRechargeDetailReq: NRechargeDetailReq ): Observable<NRechargeDetailResponse>
}