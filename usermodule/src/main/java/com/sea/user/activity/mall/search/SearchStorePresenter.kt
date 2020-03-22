package com.sea.user.activity.mall.search

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class SearchStorePresenter : IPresenter<SearchStoreContact.ISearchStoreView>(),
    SearchStoreContact.ISearchStorePresenter {



    override fun loadHotSearch() {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadHotSearchStore(NHotSearchStoreModelReq())
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
                      //  softView.get()?.loadSearchStoreSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadSearchStoreFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadSearchStoreFail(throwable) }
            )
    }

    override fun loadAddSearch(nSearchStoreModelReq: NSearchStoreModelReq) {

    }

    override fun clearHotSearch() {

    }
}