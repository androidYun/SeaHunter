package com.sea.user.presenter.user

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.UserInformApi


class UserInformPresenter : IPresenter<UserInformContact.IUserInformView>(), UserInformContact.IUserInformPresenter {
    override fun loadUserInform() {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadUserInform()
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
                        softView.get()?.loadUserInformSuccess(it)
                    } else {
                        softView.get()?.loadUserInformFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadUserInformFail(throwable) }
            )
    }
}