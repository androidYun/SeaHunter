package com.sea.custom.ui.make.list

import com.sea.custom.api.DelicacyMakeApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class DelicacyMakeListPresenter : IPresenter<DelicacyMakeListContact.IDelicacyMakeListView>(),
    DelicacyMakeListContact.IDelicacyMakeListPresenter {
    override fun loadDelicacyMakeList(nDelicacyMakeListModelReq: NDelicacyMakeListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(DelicacyMakeApi::class.java)
            .loadDelicacyMakeList(nDelicacyMakeListModelReq)
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
                        softView.get()
                            ?.loadDelicacyMakeListSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadDelicacyMakeListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDelicacyMakeListFail(throwable) }
            )
        softView.get()
            ?.loadDelicacyMakeListSuccess(
                listOf(DelicacyMakeListItem(), DelicacyMakeListItem()),
                20
            )
    }
}