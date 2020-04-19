package com.sea.custom.api

import com.sea.custom.ui.delicacy.report.NCheckReportModelReq
import com.sea.custom.ui.delicacy.report.NCheckReportModelResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckReportApi {

    @POST("getdata.ashx")
    fun loadCheckReport(@Body nCheckReportModelReq: NCheckReportModelReq): Observable<NCheckReportModelResponse>
}