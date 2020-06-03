package com.sea.custom.ui.custom

import com.sea.custom.api.CustomApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class MineCustomPresenter : IPresenter<MineCustomContact.IMineCustomView>(),
    MineCustomContact.IMineCustomPresenter {
    override fun loadMineCustom(nMineCustomModelReq: NMineCustomModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CustomApi::class.java)
            .loadMineCustom(nMineCustomModelReq)
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
                        softView.get()?.loadMineCustomSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadMineCustomFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMineCustomFail(throwable) }
            )
    }
}