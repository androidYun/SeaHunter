package com.sea.custom.ui.delicacy.vr.list

import com.sea.custom.api.StoreVrApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class StoreVrListPresenter : IPresenter<StoreVrListContact.IStoreVrListView>(),
    StoreVrListContact.IStoreVrListPresenter {
    override fun loadStoreVrList(nStoreVrListModelReq: NStoreVrModelReq) {
        RetrofitUtils.getRetrofit()
            .create(StoreVrApi::class.java)
            .loadStoreVrList(nStoreVrListModelReq)
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
                        softView.get()?.loadStoreVrListSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadStoreVrListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadStoreVrListFail(throwable) }
            )
        softView.get()?.loadStoreVrListSuccess(listOf(StoreVrItem(), StoreVrItem()), 30)
    }
}