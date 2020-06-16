package com.sea.custom.ui.club

import com.sea.custom.api.ChannelApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.presenter.channel.NChannelModelReq


class ClubMainPresenter : IPresenter<ClubMainContact.IClubView>(), ClubMainContact.IClubPresenter {
    override fun loadRecommendActivityClub(nChannelModelReq: NChannelModelReq) {
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
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadRecommendActivitySuccess(it.data)
                    } else {
                        softView.get()?.loadFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadFail(throwable) }
            )
    }

    override fun loadDelicacy(nChannelModelReq: NChannelModelReq) {
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
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadDelicacySuccess(it.data)
                    } else {
                        softView.get()?.loadFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadFail(throwable) })
    }

    override fun loadDelicacyMake(nChannelModelReq: NChannelModelReq) {
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
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadDelicacyMakeSuccess(it.data)
                    } else {
                        softView.get()?.loadFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadFail(throwable) })
    }
}