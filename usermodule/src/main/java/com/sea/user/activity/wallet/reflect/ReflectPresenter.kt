package com.sea.user.activity.wallet.reflect

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.WalletApi


class ReflectPresenter : IPresenter<ReflectContact.IReflectView>(),
    ReflectContact.IReflectPresenter {
    override fun loadReflect(nReflectModelReq: NReflectModelReq) {
        RetrofitUtils.getRetrofit()
            .create(WalletApi::class.java)
            .loadReflect(nReflectModelReq)
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
                        softView.get()?.loadReflectSuccess(it.data)
                    } else {
                        softView.get()?.loadReflectFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadReflectFail(throwable) }
            )
    }
}