package com.sea.user.activity.feedback.complaint

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.FeedBackApi


class ComplaintFeedbackPresenter : IPresenter<ComplaintFeedbackContact.IComplaintFeedbackView>(),
    ComplaintFeedbackContact.IComplaintFeedbackPresenter {
    override fun loadComplaintFeedback(nComplaintFeedbackModelReq: NComplaintFeedbackModelReq) {
        RetrofitUtils.getRetrofit()
            .create(FeedBackApi::class.java)
            .loadFeedBack(nComplaintFeedbackModelReq)
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
                        softView.get()?.loadComplaintFeedbackSuccess(it.data)
                    } else {
                        softView.get()?.loadComplaintFeedbackFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadComplaintFeedbackFail(throwable) }
            )
    }
}