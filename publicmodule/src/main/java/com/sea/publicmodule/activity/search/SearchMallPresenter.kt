package com.sea.publicmodule.activity.search

import com.sea.publicmodule.api.SearchApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class SearchMallPresenter : IPresenter<SearchMallContact.ISearchMallView>(),
    SearchMallContact.ISearchStorePresenter {

    override fun loadSearch() {
        RetrofitUtils.getRetrofit()
            .create(SearchApi::class.java)
            .loadSearchList(NSearchModelReq())
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
                        softView.get()?.loadAddSearch(it.data)
                    } else {
                        softView.get()?.loadSearchStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSearchStoreFail(throwable) }
            )
    }

    override fun loadAddSearch(nAddSearchMallModelReq: NAddSearchMallModelReq) {
        RetrofitUtils.getRetrofit()
            .create(SearchApi::class.java)
            .loadAddSearch(nAddSearchMallModelReq)
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
                        softView.get()?.addSearchSuccess()
                    } else {
                        softView.get()?.loadSearchStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSearchStoreFail(throwable) }
            )
    }

    override fun clearSearch() {
        RetrofitUtils.getRetrofit()
            .create(SearchApi::class.java)
            .loadDeleteSearch(NSearchMallModelReq())
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
                        softView.get()?.clearSearch()
                    } else {
                        softView.get()?.loadSearchStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSearchStoreFail(throwable) }
            )
    }
}