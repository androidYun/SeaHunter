package com.sea.user.activity.mall.list

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class MallListPresenter : IPresenter<MallListContact.IMallListView>(), MallListContact.IMallListPresenter {
    override fun loadMallList(nMallListModelReq: NMallListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApe::class.java)
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
                    if (it.code == 200) {
                        softView.get()?.loadMallListSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadMallListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMallListFail(throwable) }
            )
    }
}