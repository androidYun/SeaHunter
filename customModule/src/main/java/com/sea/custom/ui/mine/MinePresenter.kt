package com.sea.custom.ui.mine

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.MineApi


class MinePresenter : IPresenter<MineContact.IMineView>(), MineContact.IMinePresenter {
    override fun loadMine(nMineModelReq: NMineModelReq) {
        RetrofitUtils.getRetrofit()
            .create(MineApi::class.java)
            .loadMine()
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
                        softView.get()?.loadMineSuccess(it.data)
                    } else {
                        softView.get()?.loadMineFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMineFail(throwable) }
            )
    }
}