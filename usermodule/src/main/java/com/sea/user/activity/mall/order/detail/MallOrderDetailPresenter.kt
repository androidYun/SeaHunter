package com.sea.user.activity.mall.order.detail

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class MallOrderDetailPresenter : IPresenter<MallOrderDetailContact.IMallOrderDetailView>(),
    MallOrderDetailContact.IMallOrderDetailPresenter {
    override fun loadMallOrderDetail(nMallOrderDetailModelReq: NMallOrderDetailModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadMallOrderDetail(nMallOrderDetailModelReq)
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
                        softView.get()?.loadMallOrderDetailSuccess(it.data)
                    } else {
                        softView.get()?.loadMallOrderDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMallOrderDetailFail(throwable) }
            )
    }
}