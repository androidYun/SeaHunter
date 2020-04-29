package com.sea.custom.ui.entertainment.list

import com.sea.custom.api.EntertainmentApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class EntertainmentListPresenter : IPresenter<EntertainmentListContact.IEntertainmentListView>(),
    EntertainmentListContact.IEntertainmentListPresenter {
    override fun loadEntertainmentList(nEntertainmentListModelReq: NEntertainmentListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(EntertainmentApi::class.java)
            .loadEntertainmentList(nEntertainmentListModelReq)
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
                        softView.get()
                            ?.loadEntertainmentListSuccess(it.data.mList, it.totalCount)
                    } else {
                        softView.get()?.loadEntertainmentListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadEntertainmentListFail(throwable) }
            )
        softView.get()
            ?.loadEntertainmentListSuccess(
                listOf(EntertainmentListItem(), EntertainmentListItem()),
                20
            )
    }
}