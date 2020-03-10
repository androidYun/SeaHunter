package com.sea.user.activity.mall.detail

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.ShopDetailApi


class ShopDetailPresenter : IPresenter<ShopDetailContact.IShopDetailView>(),
    ShopDetailContact.IShopDetailPresenter {
    override fun loadShopDetail(nShopDetailModelReq: NShopDetailModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopDetailApi::class.java)
            .loadShopDetail(nShopDetailModelReq)
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
                        softView.get()?.loadShopDetailSuccess(it.data)
                    } else {
                        softView.get()?.loadShopDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopDetailFail(throwable) }
            )
    }
}