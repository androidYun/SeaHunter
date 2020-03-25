package com.sea.user.activity.register

import com.sea.user.api.UserInformApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class RegisterPresenter : IPresenter<RegisterContract.IRegisterView>(), RegisterContract.IRegisterPresenter {

    override fun loadRegister(nRegisterModelReq: NRegisterModelReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadRegister(nRegisterModelReq)
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
                        softView.get()?.loadRegisterSuccess()
                    } else {
                        softView.get()?.loadRegisterFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadRegisterFail(throwable) }
            )
    }
}