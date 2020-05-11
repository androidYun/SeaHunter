package com.sea.custom.presenter.collection

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.DelicacyCollectionApi


class DelicacyCollectionPresenter : IPresenter<DelicacyCollectionContact.IDelicacyCollectionView>(),
    DelicacyCollectionContact.IDelicacyCollectionPresenter {
    override fun loadDelicacyCollection(nDelicacyCollectionModelReq: NDelicacyCollectionModelReq) {
        RetrofitUtils.getRetrofit()
            .create(DelicacyCollectionApi::class.java)
            .loadDelicacyCollection(nDelicacyCollectionModelReq)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadDelicacyCollectionSuccess()
                    } else {
                        softView.get()?.loadDelicacyCollectionFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDelicacyCollectionFail(throwable) }
            )
    }

    override fun cancelDelicacyCollection(nDelicacyCollectionModelReq: NCancelDelicacyCollectionModelReq) {
        RetrofitUtils.getRetrofit()
            .create(DelicacyCollectionApi::class.java)
            .loadCancelDelicacyCollection(nDelicacyCollectionModelReq)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadDelicacyCollectionSuccess()
                    } else {
                        softView.get()?.loadDelicacyCollectionFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDelicacyCollectionFail(throwable) }
            )
    }
}