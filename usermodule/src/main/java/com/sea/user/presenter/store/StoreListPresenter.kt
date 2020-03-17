package com.sea.user.presenter.store

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class StoreListPresenter : IPresenter<StoreListContact.IStoreListView>(), StoreListContact.IStoreListPresenter {
    override fun loadStoreList(nStoreListModelReq: NStoreListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadStoreList(nStoreListModelReq)
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
                        softView.get()?.loadStoreListSuccess(it.data)
                    } else {
                        softView.get()?.loadStoreListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadStoreListFail(throwable) }
            )
    }
}