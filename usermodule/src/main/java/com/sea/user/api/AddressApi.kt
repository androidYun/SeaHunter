package com.sea.user.api

import com.sea.user.activity.address.NAddressDetailModelResponse
import com.sea.user.activity.address.NAddressModelReq
import com.sea.user.activity.address.list.NAddressListModelReq
import com.sea.user.activity.address.list.NAddressListModelResponse
import com.sea.user.activity.model.BaseEntry
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
interface AddressApi {


    @POST("getdata.ashx")
    fun loadAddAddress(@Body nAddressModelReq: NAddressModelReq): Observable<BaseEntry>


    @POST("getdata.ashx")
    fun loadAddressList(@Body nAddressListModelReq: NAddressListModelReq): Observable<NAddressListModelResponse>

    @POST("getdata.ashx")
    fun loadAddressDetail(@Body addressId: Int): Observable<NAddressDetailModelResponse>

    @POST("getdata.ashx")
    fun modifyAddressDetail(@Body nAddressModelReq: NAddressModelReq): Observable<BaseEntry>


}