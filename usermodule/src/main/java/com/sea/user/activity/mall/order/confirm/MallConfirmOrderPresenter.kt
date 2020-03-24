package com.sea.user.activity.mall.order.confirm

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.ShopApi


class MallConfirmOrderPresenter : IPresenter<MallConfirmOrderContact.IMallConfirmOrderView>(),
    MallConfirmOrderContact.IMallConfirmOrderPresenter {
    override fun loadMallConfirmOrder(nMallConfirmOrderModelReq: NMallConfirmOrderModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadMallConfirmOrder(nMallConfirmOrderModelReq)
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
                        softView.get()?.loadMallConfirmOrderSuccess(it.data.mList)
                    } else {
                        softView.get()?.loadMallConfirmOrderFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMallConfirmOrderFail(throwable) }
            )
    }
}