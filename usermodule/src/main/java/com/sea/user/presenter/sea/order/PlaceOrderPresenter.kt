package com.sea.user.presenter.sea.order

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.ShopApi


class PlaceOrderPresenter : IPresenter<PlaceOrderContact.IPlaceOrderView>(), PlaceOrderContact.IPlaceOrderPresenter {
    override fun loadPlaceOrder(nPlaceOrderModelReq: NPlaceOrderModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadPlaceOrder(nPlaceOrderModelReq)
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
                        softView.get()?.loadPlaceOrderSuccess(it.data)
                    } else {
                        softView.get()?.loadPlaceOrderFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadPlaceOrderFail(throwable) }
            )
    }
}