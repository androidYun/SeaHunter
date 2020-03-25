package com.sea.user.presenter.car

import com.sea.user.activity.mall.car.NEditShopCarModelReq
import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ShopCarEditPresenter : IPresenter<ShopCarEditContact.IShopCarEditView>(),
    ShopCarEditContact.IShopCarEditPresenter {
    override fun loadShopCarEdit(nShopCarEditModelReq: NEditShopCarModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadEditShopCar(nShopCarEditModelReq)
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
                        softView.get()?.loadShopCarEditSuccess()
                    } else {
                        softView.get()?.loadShopCarEditFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopCarEditFail(throwable) }
            )
    }


}