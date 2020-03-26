package com.sea.user.activity.mall.search

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class SearchMallPresenter : IPresenter<SearchMallContact.ISearchMallView>(),
    SearchMallContact.ISearchStorePresenter {



    override fun loadHotSearch() {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadHotSearchMall(NHotSearchMallModelReq())
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
                    if (it.code==1) {
                        softView.get()?.loadHotSearchSuccess(it.data)
                    } else {
                        softView.get()?.loadSearchStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSearchStoreFail(throwable) }
            )
    }

    override fun loadAddSearch(nAddSearchMallModelReq: NAddSearchMallModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadAddSearch(NAddSearchMallModelReq())
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
                    if (it.code==1) {
                          softView.get()?.loadAddSearch()
                    } else {
                        softView.get()?.loadSearchStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSearchStoreFail(throwable) }
            )
    }

    override fun clearHotSearch() {

    }
}