package com.sea.user.api

import com.sea.user.presenter.update.NUpdateImageResponse
import com.sea.user.presenter.version.NVersionCodeModelReq
import com.sea.user.presenter.version.NVersionCodeResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 16/03/2020.
 * description:
 */
interface CommonApi {
    @Multipart
    @POST("upload.ashx")
    fun loadUpdateImage(@Query("type") type: String,  @Part file: MultipartBody.Part): Observable<NUpdateImageResponse>


    @POST("getdata.ashx")
    fun loadVersionCode(@Body nVersionCodeModelReq: NVersionCodeModelReq): Observable<NVersionCodeResponse>
}