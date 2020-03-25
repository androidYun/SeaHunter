package com.sea.user.activity.integral.mall

import com.sea.user.api.IntegralApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class IntegralMallPresenter : IPresenter<IntegralMallContact.IIntegralMallView>(),
    IntegralMallContact.IIntegralMallPresenter {
    override fun loadIntegralMall(nIntegralMallModelReq: NIntegralMallModelReq) {
        RetrofitUtils.getRetrofit()
            .create(IntegralApi::class.java)
            .loadIntegralMall(nIntegralMallModelReq)
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
                    if (it.code==1) {
                        softView.get()?.loadIntegralMallSuccess(it.data.mList)
                    } else {
                        softView.get()?.loadIntegralMallFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadIntegralMallFail(throwable) }
            )
    }
}