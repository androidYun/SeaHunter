package com.sea.custom.api

import com.sea.custom.presenter.category.NCategoryModelReq
import com.sea.custom.presenter.category.NCategoryResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CategoryApi {

    @POST("getdata.ashx")
    fun loadCategory(@Body nCategoryModelReq: NCategoryModelReq): Observable<NCategoryResponse>
}