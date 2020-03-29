package com.sea.user.presenter.sea.order.cancel

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.ShopApi


class CancelOrderPresenter : IPresenter<CancelOrderContact.ICancelOrderView>(),
    CancelOrderContact.ICancelOrderPresenter {
    override fun loadCancelOrder(nCancelOrderModelReq: NCancelOrderModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadCancelOrder(nCancelOrderModelReq)
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
                        softView.get()?.loadCancelOrderSuccess()
                    } else {
                        softView.get()?.loadCancelOrderFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCancelOrderFail(throwable) }
            )
    }
}