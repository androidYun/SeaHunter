package com.sea.user.activity.mall

import com.sea.user.api.ShopApi
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class SeaFoodMallPresenter : IPresenter<SeaFoodMallContact.ISeaFoodMallView>(),
    SeaFoodMallContact.ISeaFoodMallPresenter {
    override fun loadSeaCategory() {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadSeaCategory(NSeaFoodMallModelReq())
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
                    if (it.code==1) {
                        softView.get()?.loadSeaCategoryListSuccess(it.data)
                    } else {
                        softView.get()?.loadSeaFoodMallFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSeaFoodMallFail(throwable) }
            )
    }

    override fun loadMallListRecommend(nMallListModelReq: NMallListModelReq) {
        nMallListModelReq.is_red = 1
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadMallList(nMallListModelReq)
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
                    if (it.code==1) {
                        softView.get()?.loadMallListRecommendSuccess(it.data)
                    } else {
                        softView.get()?.loadSeaFoodMallFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSeaFoodMallFail(throwable) }
            )
    }

    override fun loadMallListHot(nMallListModelReq: NMallListModelReq) {
        nMallListModelReq.is_hot = 1
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadMallList(nMallListModelReq)
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
                    if (it.code==1) {
                        softView.get()?.loadMallListHotSuccess(it.data)
                    } else {
                        softView.get()?.loadSeaFoodMallFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSeaFoodMallFail(throwable) }
            )
    }
}