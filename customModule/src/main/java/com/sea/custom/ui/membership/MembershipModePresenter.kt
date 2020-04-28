package com.sea.custom.ui.membership

import com.sea.custom.api.MembershipApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class MembershipModePresenter : IPresenter<MembershipModeContact.IMembershipModeView>(),
    MembershipModeContact.IMembershipModePresenter {
    override fun loadMembershipMode(nMembershipModeModelReq: NMembershipModeModelReq) {
        RetrofitUtils.getRetrofit()
            .create(MembershipApi::class.java)
            .loadMembershipMode(nMembershipModeModelReq)
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
                        softView.get()?.loadMembershipModeSuccess(it.data)
                    } else {
                        softView.get()?.loadMembershipModeFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadMembershipModeFail(throwable) }
            )
    }
}