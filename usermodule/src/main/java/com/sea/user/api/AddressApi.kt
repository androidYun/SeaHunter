package com.sea.user.api

import com.sea.user.activity.address.NAddressModelReq
import com.sea.user.activity.model.BaseEntry
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 05/03/2020.
 * description:
 */
interface AddressApi {


    @POST
    fun loadAddAddress(@Body nAddressModelReq: NAddressModelReq): Observable<BaseEntry>
}