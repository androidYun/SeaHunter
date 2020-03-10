package com.sea.user.activity.mall.select

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class SelectStorePresenter : IPresenter<SelectStoreContact.ISelectStoreView>(),
    SelectStoreContact.ISelectStorePresenter {
    override fun loadSelectStore(nSelectStoreModelReq: NSelectStoreModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadSelectStore(nSelectStoreModelReq)
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
                        softView.get()?.loadSelectStoreSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadSelectStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSelectStoreFail(throwable) }
            )
    }
}