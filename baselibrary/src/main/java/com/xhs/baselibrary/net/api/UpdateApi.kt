package com.xhs.baselibrary.net.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 05/05/2019.
 * description:
 */
interface UpdateApi {
    @Streaming
    @GET
    fun download(@Url url: String): Observable<ResponseBody>
}