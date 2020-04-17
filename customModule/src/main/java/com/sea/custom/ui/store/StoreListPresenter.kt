package com.sea.custom.ui.store

import com.sea.custom.api.StoreApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class StoreListPresenter : IPresenter<StoreListContact.IStoreListView>(),
    StoreListContact.IStoreListPresenter {
    override fun loadStoreList(nStoreListModelReq: NStoreListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(StoreApi::class.java)
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
                    if (it.code == 200) {
                        softView.get()?.loadStoreListSuccess(it.data.mList, it.totalCount)
                    } else {
                        softView.get()?.loadStoreListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadStoreListFail(throwable) }
            )
    }
}