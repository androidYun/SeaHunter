package com.sea.custom.ui.delicacy.report

import com.sea.custom.api.CheckReportApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class CheckReportPresenter : IPresenter<CheckReportContact.ICheckReportView>(),
    CheckReportContact.ICheckReportPresenter {
    override fun loadCheckReport(nCheckReportModelReq: NCheckReportModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CheckReportApi::class.java)
            .loadCheckReport(nCheckReportModelReq)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
                onStop()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadCheckReportSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadCheckReportFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCheckReportFail(throwable) }
            )
    }
}