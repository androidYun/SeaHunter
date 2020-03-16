package com.sea.user.presenter.version

import com.sea.user.api.CommonApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class VersionCodePresenter : IPresenter<VersionCodeContact.IVersionCodeView>(),
    VersionCodeContact.IVersionCodePresenter {
    override fun loadVersionCode(nVersionCodeModelReq: NVersionCodeModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CommonApi::class.java)
            .loadVersionCode(nVersionCodeModelReq)
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
                        softView.get()?.loadVersionCodeSuccess(it.data)
                    } else {
                        softView.get()?.loadVersionCodeFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadVersionCodeFail(throwable) }
            )
    }
}