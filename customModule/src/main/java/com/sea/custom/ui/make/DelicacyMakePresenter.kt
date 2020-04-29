package com.sea.custom.ui.make

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.DelicacyMakeApi


class DelicacyMakePresenter : IPresenter<DelicacyMakeContact.IDelicacyMakeView>(),
    DelicacyMakeContact.IDelicacyMakePresenter {
    override fun loadDelicacyMake(nDelicacyMakeModelReq: NDelicacyMakeModelReq) {
        RetrofitUtils.getRetrofit()
            .create(DelicacyMakeApi::class.java)
            .loadDelicacyMake(nDelicacyMakeModelReq)
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
                        softView.get()?.loadDelicacyMakeSuccess(it.data)
                    } else {
                        softView.get()?.loadDelicacyMakeFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDelicacyMakeFail(throwable) }
            )
    }
}