package com.sea.user.activity.shop.order

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ShopOrderPresenter : IPresenter<ShopOrderContact.IShopOrderView>(),
    ShopOrderContact.IShopOrderPresenter {
    override fun loadShopOrder(nShopOrderModelReq: NShopOrderModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadShopOrder(nShopOrderModelReq)
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
                    if (it.code == 0) {
                        softView.get()?.loadShopOrderSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadShopOrderFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopOrderFail(throwable) }
            )
    }
}