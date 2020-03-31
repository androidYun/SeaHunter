package com.sea.user.activity.phone

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.user.api.UserInformApi


class ModifyPhonePresenter : IPresenter<ModifyPhoneContact.IModifyPhoneView>(),
    ModifyPhoneContact.IModifyPhonePresenter {
    override fun loadModifyPhone(nModifyPhoneModelReq: NModifyPhoneModelReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadModifyPhone(nModifyPhoneModelReq)
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
                        softView.get()?.loadModifyPhoneSuccess(nModifyPhoneModelReq.mobile)
                    } else {
                        softView.get()?.loadModifyPhoneFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadModifyPhoneFail(throwable) }
            )
    }
}