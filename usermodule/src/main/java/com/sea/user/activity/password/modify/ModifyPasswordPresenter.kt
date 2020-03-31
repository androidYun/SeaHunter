package com.sea.user.activity.password.modify

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.UserInformApi


class ModifyPasswordPresenter : IPresenter<ModifyPasswordContact.IModifyPasswordView>(),
    ModifyPasswordContact.IModifyPasswordPresenter {
    override fun loadModifyPassword(nModifyPasswordModelReq: NModifyPasswordModelReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadModifyPassword(nModifyPasswordModelReq)
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
                        softView.get()?.loadModifyPasswordSuccess(nModifyPasswordModelReq.new_pwd)
                    } else {
                        softView.get()?.loadModifyPasswordFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadModifyPasswordFail(throwable) }
            )
    }
}