package com.sea.custom.presenter.banner

import com.sea.custom.api.CommonApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class BannerPresenter : IPresenter<BannerContact.IBannerView>(), BannerContact.IBannerPresenter {
    override fun loadBanner(nBannerModelReq: NBannerModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CommonApi::class.java)
            .loadBanner(nBannerModelReq)
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