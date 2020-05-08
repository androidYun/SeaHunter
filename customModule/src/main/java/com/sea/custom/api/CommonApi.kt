package com.sea.custom.api

import com.sea.custom.presenter.banner.NBannerModelReq
import com.sea.custom.presenter.banner.NBannerResponse
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

    @POST("getdata.ashx")
    fun loadBanner(@Body nBannerModelReq: NBannerModelReq): Observable<NBannerResponse>
}