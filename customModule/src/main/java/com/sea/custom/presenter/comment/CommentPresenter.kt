package com.sea.custom.presenter.comment

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.CommentApi


class CommentPresenter : IPresenter<CommentContact.ICommentView>(),
    CommentContact.ICommentPresenter {
    override fun loadComment(nCommentModelReq: NCommentModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CommentApi::class.java)
            .loadComment(nCommentModelReq)
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
                        softView.get()?.loadCommentSuccess(it.data)
                    } else {
                        softView.get()?.loadCommentFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCommentFail(throwable) }
            )
    }

    override fun submitComment(nCommentModelReq: NSubmitCommentModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CommentApi::class.java)
            .submitComment(nCommentModelReq)
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
                        softView.get()?.submitCommentSuccess()
                    } else {
                        softView.get()?.loadCommentFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCommentFail(throwable) }
            )
    }
}