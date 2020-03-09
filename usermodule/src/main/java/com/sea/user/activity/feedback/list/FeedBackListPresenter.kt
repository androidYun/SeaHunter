package com.sea.user.activity.feedback.list

import com.sea.user.api.FeedBackApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class FeedBackListPresenter : IPresenter<FeedBackListContact.IFeedBackListView>(),
    FeedBackListContact.IFeedBackListPresenter {
    override fun loadFeedBackList(nFeedBackListModelReq: NFeedBackListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(FeedBackApi::class.java)
            .loadFeedBackList(nFeedBackListModelReq)
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
                        softView.get()?.loadFeedBackListSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadFeedBackListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadFeedBackListFail(throwable) }
            )
    }
}