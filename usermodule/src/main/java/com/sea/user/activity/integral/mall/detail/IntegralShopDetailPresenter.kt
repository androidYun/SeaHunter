package com.sea.user.activity.integral.mall.detail

import com.sea.user.api.IntegralApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class IntegralShopDetailPresenter : IPresenter<IntegralShopDetailContact.IIntegralShopDetailView>(),
    IntegralShopDetailContact.IIntegralShopDetailPresenter {
    override fun loadIntegralShopDetail(nIntegralShopDetailModelReq: NIntegralShopDetailModelReq) {
        RetrofitUtils.getRetrofit()
            .create(IntegralApi::class.java)
            .loadIntegralShopDetail(nIntegralShopDetailModelReq)
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
                        softView.get()?.loadIntegralShopDetailSuccess(it.data)
                    } else {
                        softView.get()?.loadIntegralShopDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadIntegralShopDetailFail(throwable) }
            )
    }
}