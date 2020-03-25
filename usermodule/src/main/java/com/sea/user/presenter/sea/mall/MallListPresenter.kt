package com.sea.user.presenter.sea.mall

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class MallListPresenter : IPresenter<MallListContact.IMallListView>(), MallListContact.IMallListPresenter {
    override fun loadMallList(nMallListModelReq: NMallListModelReq) {
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
                onStop()
            }
            .subscribe(
                {
                    if (it.code==1) {
                        softView.get()?.loadMallListSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadMallListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMallListFail(throwable) }
            )
    }
}