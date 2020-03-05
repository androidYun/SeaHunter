package com.sea.user.activity.center

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.UserInformApi


class UserCenterPresenter : IPresenter<UserCenterContact.IUserCenterView>(), UserCenterContact.IUserCenterPresenter {
    override fun loadUserCenter(nUserCenterModelReq: NUserCenterModelReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadUserInform(nUserCenterModelReq)
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
                        softView.get()?.loadUserCenterSuccess(it.data)
                    } else {
                        softView.get()?.loadUserCenterFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadUserCenterFail(throwable) }
            )
    }
}