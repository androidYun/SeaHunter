package com.sea.user.activity.integral.detail

import com.sea.user.api.IntegralApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class IntegralDetailPresenter : IPresenter<IntegralDetailContact.IIntegralDetailView>(),
    IntegralDetailContact.IIntegralDetailPresenter {
    override fun loadIntegralDetail(nIntegralDetailModelReq: NIntegralDetailModelReq) {
        RetrofitUtils.getRetrofit()
            .create(IntegralApi::class.java)
            .loadIntegralMall(nIntegralDetailModelReq)
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
                        softView.get()?.loadIntegralDetailSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadIntegralDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadIntegralDetailFail(throwable) }
            )
    }
}