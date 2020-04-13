package com.sea.custom.ui.delicacy

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.DelicacyApi


class DelicacyPresenter : IPresenter<DelicacyContact.IDelicacyView>(),
    DelicacyContact.IDelicacyPresenter {
    override fun loadDelicacy(nDelicacyModelReq: NDelicacyModelReq) {
        RetrofitUtils.getRetrofit()
            .create(DelicacyApi::class.java)
            .loadDelicacy(nDelicacyModelReq)
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
                        softView.get()?.loadDelicacySuccess(it.data)
                    } else {
                        softView.get()?.loadDelicacyFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDelicacyFail(throwable) }
            )
    }
}