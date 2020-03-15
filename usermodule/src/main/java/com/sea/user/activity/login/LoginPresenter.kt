package com.sea.user.activity.login

import com.sea.user.api.UserInformApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class LoginPresenter : IPresenter<LoginContact.ILoginView>(), LoginContact.ILoginPresenter {
    override fun loadLogin(nLoginModelReq: NLoginModelReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadLogin(nLoginModelReq)
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
                        softView.get()?.loadLoginSuccess(it.data,nLoginModelReq.phone,nLoginModelReq.password)
                    } else {
                        softView.get()?.loadLoginFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadLoginFail(throwable) }
            )
    }
}