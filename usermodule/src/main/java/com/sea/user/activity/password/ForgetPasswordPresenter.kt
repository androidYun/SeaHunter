package com.sea.user.activity.password

import com.sea.user.api.UserInformApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.base.example.ExampleApi
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.xhs.prison.model.NForgetPasswordModelReq

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class ForgetPasswordPresenter : IPresenter<ForgetPasswordContract.IForgetPasswordView>(),
    ForgetPasswordContract.IForgetPasswordPresenter {

    override fun loadForgetPassword(nForgetPasswordModelReq: NForgetPasswordModelReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadForgetPassword(nForgetPasswordModelReq)
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
                        softView.get()?.loadForgetPasswordSuccess()
                    } else {
                        softView.get()?.loadForgetPasswordFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadForgetPasswordFail(throwable) }
            )
    }
}