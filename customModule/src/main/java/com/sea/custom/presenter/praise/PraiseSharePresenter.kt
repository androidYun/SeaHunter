package com.sea.custom.presenter.praise

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.PraiseShareApi


class PraiseSharePresenter : IPresenter<PraiseShareContact.IPraiseShareView>(),
    PraiseShareContact.IPraiseSharePresenter {
    override fun loadPraiseShare(nPraiseShareModelReq: NPraiseShareModelReq) {
        RetrofitUtils.getRetrofit()
            .create(PraiseShareApi::class.java)
            .loadPraiseShare(nPraiseShareModelReq)
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
                        softView.get()?.loadPraiseShareSuccess(it)
                    } else {
                        softView.get()?.loadPraiseShareFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadPraiseShareFail(throwable) }
            )
    }
}