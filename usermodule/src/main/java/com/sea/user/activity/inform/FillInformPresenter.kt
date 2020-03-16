package com.sea.user.activity.inform

import com.sea.user.api.UserInformApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.base.example.ExampleApi
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.xhs.prison.model.NFillInformReq

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class FillInformPresenter : IPresenter<FillInformContract.IFillInformView>(), FillInformContract.IFillInformPresenter {

    override fun loadFillInform(nFillInformReq: NFillInformReq) {
        RetrofitUtils.getRetrofit()
            .create(UserInformApi::class.java)
            .loadFillInform(nFillInformReq)
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
                        softView.get()?.loadFillInformSuccess()
                    } else {
                        softView.get()?.loadFillInformFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadFillInformFail(throwable) }
            )
    }
}