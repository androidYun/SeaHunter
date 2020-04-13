package com.sea.custom.ui.entertainment

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.EntertainmentApi


class EntertainmentPresenter : IPresenter<EntertainmentContact.IEntertainmentView>(),
    EntertainmentContact.IEntertainmentPresenter {
    override fun loadEntertainment(nEntertainmentModelReq: NEntertainmentModelReq) {
        RetrofitUtils.getRetrofit()
            .create(EntertainmentApi::class.java)
            .loadEntertainment(nEntertainmentModelReq)
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
                        softView.get()?.loadEntertainmentSuccess(it.data)
                    } else {
                        softView.get()?.loadEntertainmentFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadEntertainmentFail(throwable) }
            )
    }
}