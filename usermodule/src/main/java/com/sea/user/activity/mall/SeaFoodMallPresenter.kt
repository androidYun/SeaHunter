package com.sea.user.activity.mall

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class SeaFoodMallPresenter : IPresenter<SeaFoodMallContact.ISeaFoodMallView>(),
    SeaFoodMallContact.ISeaFoodMallPresenter {
    override fun loadSeaFoodMall(nSeaFoodMallModelReq: NSeaFoodMallModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadSeaFoodMall(nSeaFoodMallModelReq)
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
                        softView.get()?.loadSeaFoodMallSuccess(it.data)
                    } else {
                        softView.get()?.loadSeaFoodMallFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSeaFoodMallFail(throwable) }
            )
    }
}