package com.sea.user.activity.integral.exchange

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.IntegralApi


class ExchangeListPresenter : IPresenter<ExchangeListContact.IExchangeListView>(),
    ExchangeListContact.IExchangeListPresenter {
    override fun loadExchangeList(nExchangeListModelReq: NExchangeListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(IntegralApi::class.java)
            .loadExchangeList(nExchangeListModelReq)
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
                        softView.get()?.loadExchangeListSuccess(it.data.mList, it.totalCount)
                    } else {
                        softView.get()?.loadExchangeListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadExchangeListFail(throwable) }
            )
    }
}