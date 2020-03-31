package com.sea.user.activity.mall.car

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ShopCarPresenter : IPresenter<ShopCarContact.IShopCarView>(),
    ShopCarContact.IShopCarPresenter {
    override fun loadShopCar(nShopCarModelReq: NShopCarModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadShopCar(nShopCarModelReq)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadShopCarSuccess(it.data)
                    } else {
                        softView.get()?.loadShopCarFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopCarFail(throwable) }
            )
    }

    override fun loadDeleteShopCar(nDeleteShopCarModelReq: NDeleteShopCarModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadDeleteShopCar(nDeleteShopCarModelReq)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadDeleteShopCarSuccess()
                    } else {
                        softView.get()?.loadDeleteShopCarFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDeleteShopCarFail(throwable) }
            )
    }
}