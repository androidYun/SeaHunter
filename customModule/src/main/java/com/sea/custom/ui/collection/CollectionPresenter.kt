package com.sea.custom.ui.collection

import com.sea.custom.api.CollectionApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class CollectionPresenter : IPresenter<CollectionContact.ICollectionView>(),
    CollectionContact.ICollectionPresenter {
    override fun loadCollection(nCollectionModelReq: NCollectionModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CollectionApi::class.java)
            .loadCollection(nCollectionModelReq)
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
                        softView.get()?.loadCollectionSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadCollectionFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCollectionFail(throwable) }
            )
    }
}