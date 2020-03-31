package com.sea.user.presenter.banner

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.CommonApi


class BannerPresenter : IPresenter<BannerContact.IBannerView>(), BannerContact.IBannerPresenter {
    override fun loadBanner() {
        RetrofitUtils.getRetrofit()
            .create(CommonApi::class.java)
            .loadBanner(NBannerModelReq())
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
                        softView.get()?.loadBannerSuccess(it.data)
                    } else {
                        softView.get()?.loadBannerFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadBannerFail(throwable) }
            )
    }
}