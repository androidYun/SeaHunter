package com.sea.user.activity.wallet

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.WalletApi


class RechargeDetailPresenter : IPresenter<RechargeDetailContact.IRechargeDetailView>(),
    RechargeDetailContact.IRechargeDetailPresenter {
    override fun loadRechargeDetail(nRechargeDetailReq: NRechargeDetailReq) {
        RetrofitUtils.getRetrofit()
            .create(WalletApi::class.java)
            .loadRechargeDetail(nRechargeDetailReq)
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
                    if (it.code==1) {
                        softView.get()?.loadRechargeDetailSuccess(it.data,it.totalCount)
                    } else {
                        softView.get()?.loadRechargeDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadRechargeDetailFail(throwable) }
            )
    }
}