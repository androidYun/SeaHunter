package com.xhs.baselibrary.base.example

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 29/04/2019.
 * description:
 */
class ExamplePresenter : IPresenter<ExampleContract.ExampleView>(), ExampleContract.ExamplePresenter {
    override fun goLogin(phone: String, password: String) {
        RetrofitUtils.getRetrofit()
                .create(ExampleApi::class.java)
                .exampleLogin(phone)
                .compose(RxUtils.getSchedulerTransformer<Any>())
                .compose(RxUtils.bindToLifecycle<Any>(softView.get()))
                .doOnSubscribe { disposable ->
                    addDisposable(disposable)
                    softView.get()?.showLoading()
                }.doFinally { softView.get()?.hideLoading() }
                .subscribe(
                        {
                            //这里面是回调成功的方法
                        }, { throwable -> softView.get()?.loadLoginFail(throwable) }
                )
    }
}
