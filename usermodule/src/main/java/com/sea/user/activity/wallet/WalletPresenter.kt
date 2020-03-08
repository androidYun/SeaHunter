package com.sea.user.activity.wallet

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.WalletApi


class WalletPresenter : IPresenter<WalletContact.IWalletView>(), WalletContact.IWalletPresenter {
    override fun loadWallet(nWalletModelReq: NWalletModelReq) {
        RetrofitUtils.getRetrofit()
            .create(WalletApi::class.java)
            .loadWallet(nWalletModelReq)
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
                        softView.get()?.loadWalletSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadWalletFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadWalletFail(throwable) }
            )
    }
}