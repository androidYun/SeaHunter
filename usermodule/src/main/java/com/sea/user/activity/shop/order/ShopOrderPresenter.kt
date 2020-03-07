package com.sea.user.activity.order

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.ShopOrderApi


class ShopOrderPresenter : IPresenter<ShopOrderContact.IShopOrderView>(),
    ShopOrderContact.IShopOrderPresenter {
    override fun loadShopOrder(nShopOrderModelReq: NShopOrderModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopOrderApi::class.java)
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
                    if (it.code == 200) {
                        softView.get()?.loadShopOrderSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadShopOrderFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopOrderFail(throwable) }
            )
    }
}