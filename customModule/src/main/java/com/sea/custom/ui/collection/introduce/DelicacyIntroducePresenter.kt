package com.sea.custom.ui.collection.introduce

import com.sea.custom.api.DelicacyApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class DelicacyIntroducePresenter : IPresenter<DelicacyIntroduceContact.IDelicacyIntroduceView>(),
    DelicacyIntroduceContact.IDelicacyIntroducePresenter {
    override fun loadDelicacyIntroduce(nDelicacyIntroduceModelReq: NDelicacyIntroduceModelReq) {
        RetrofitUtils.getRetrofit()
            .create(DelicacyApi::class.java)
            .loadDelicacyIntroduce(nDelicacyIntroduceModelReq)
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
                        softView.get()
                            ?.loadDelicacyIntroduceSuccess(it.data, it.totalCount)
                    } else {
                        softView.get()?.loadDelicacyIntroduceFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDelicacyIntroduceFail(throwable) }
            )
    }
}