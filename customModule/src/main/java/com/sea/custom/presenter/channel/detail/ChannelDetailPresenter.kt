package com.sea.custom.presenter.channel.detail

import com.sea.custom.api.ChannelApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ChannelDetailPresenter : IPresenter<ChannelDetailContact.IChannelDetailView>(),
    ChannelDetailContact.IChannelDetailPresenter {
    override fun loadChannelDetail(nChannelDetailModelReq: NChannelDetailModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ChannelApi::class.java)
            .loadChannelDetail(nChannelDetailModelReq)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadChannelDetailSuccess(it.data)
                    } else {
                        softView.get()?.loadChannelDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadChannelDetailFail(throwable) }
            )
    }
}