package com.sea.user.activity.integral.shop

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.IntegralApi


class ExchangeShopPresenter : IPresenter<ExchangeShopContact.IExchangeShopView>(),
    ExchangeShopContact.IExchangeShopPresenter {
    override fun loadExchangeShop(nExchangeShopModelReq: NExchangeShopModelReq) {
        RetrofitUtils.getRetrofit()
            .create(IntegralApi::class.java)
            .loadExchangeShop(nExchangeShopModelReq)
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
                        softView.get()?.loadExchangeShopSuccess(it.data)
                    } else {
                        softView.get()?.loadExchangeShopFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadExchangeShopFail(throwable) }
            )
    }
}