package com.sea.custom.presenter.channel

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.ChannelApi


class ChannelPresenter : IPresenter<ChannelContact.IChannelView>(),
    ChannelContact.IChannelPresenter {
    override fun loadChannel(nChannelModelReq: NChannelModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ChannelApi::class.java)
            .loadChannel(nChannelModelReq)
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
                        softView.get()?.loadChannelSuccess(it.data,it.totalCount)
                    } else {
                        softView.get()?.loadChannelFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadChannelFail(throwable) }
            )
    }
}