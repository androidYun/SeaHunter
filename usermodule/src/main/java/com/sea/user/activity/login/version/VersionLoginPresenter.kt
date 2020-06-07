package com.sea.user.activity.login.version

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.VersionLoginApi


class VersionLoginPresenter : IPresenter<VersionLoginContact.IVersionLoginView>(),
    VersionLoginContact.IVersionLoginPresenter {
    override fun loadVersionLogin(nVersionLoginModelReq: NVersionLoginModelReq) {
        RetrofitUtils.getRetrofit()
            .create(VersionLoginApi::class.java)
            .loadVersionLogin(nVersionLoginModelReq)
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
                        softView.get()
                            ?.loadVersionLoginSuccess(it.data, nVersionLoginModelReq.phone)
                    } else {
                        softView.get()?.loadVersionLoginFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadVersionLoginFail(throwable) }
            )
    }
}