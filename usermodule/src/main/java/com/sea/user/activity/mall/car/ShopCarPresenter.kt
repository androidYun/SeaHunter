package com.sea.user.activity.mall.car

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.ShopCarApi


class ShopCarPresenter : IPresenter<ShopCarContact.IShopCarView>(), ShopCarContact.IShopCarPresenter {
    override fun loadShopCar(nShopCarModelReq: NShopCarModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopCarApi::class.java)
            .loadShopCar(nShopCarModelReq)
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
                    if (it.code == 200) {
                        softView.get()?.loadShopCarSuccess(it.data.mList)
                    } else {
                        softView.get()?.loadShopCarFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopCarFail(throwable) }
            )
    }
}