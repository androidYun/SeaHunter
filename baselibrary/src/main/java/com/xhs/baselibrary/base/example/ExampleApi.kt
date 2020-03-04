package com.xhs.baselibrary.base.example

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/09/2019.
 * description:
 */
interface ExampleApi {
    /**
     * 获取分组联系人
     *
     * @return
     */
    @GET("v1/api/test")
    fun exampleLogin(@Query("phoneNumber") phoneNumber:String): Observable<Any>
}