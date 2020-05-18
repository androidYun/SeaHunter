package com.sea.publicmodule.presenter.version

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.publicmodule.api.CheckVersionApi


class CheckVersionPresenter : IPresenter<CheckVersionContact.ICheckVersionView>(),
    CheckVersionContact.ICheckVersionPresenter {
    override fun loadCheckVersion(nCheckVersionModelReq: NCheckVersionModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CheckVersionApi::class.java)
            .loadCheckVersion(nCheckVersionModelReq)
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
                        softView.get()?.loadCheckVersionSuccess(it.data)
                    } else {
                        softView.get()?.loadCheckVersionFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCheckVersionFail(throwable) }
            )
    }
}