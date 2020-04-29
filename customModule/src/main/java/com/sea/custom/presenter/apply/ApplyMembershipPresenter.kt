package com.sea.custom.presenter.apply

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.MembershipApi


class ApplyMembershipPresenter : IPresenter<ApplyMembershipContact.IApplyMembershipView>(),
    ApplyMembershipContact.IApplyMembershipPresenter {
    override fun loadApplyMembership(nApplyMembershipReq: NApplyMembershipReq) {
        RetrofitUtils.getRetrofit()
            .create(MembershipApi::class.java)
            .loadApplyMembership(nApplyMembershipReq)
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
                        softView.get()?.loadApplyMembershipSuccess()
                    } else {
                        softView.get()?.loadApplyMembershipFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadApplyMembershipFail(throwable) }
            )
    }
}