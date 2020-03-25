package com.sea.user.presenter.address

import com.sea.user.activity.address.list.NAddressListModelReq
import com.sea.user.api.AddressApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 23/03/2020.
 * description:
 */
class DefaultAddressPresenter : IPresenter<DefaultAddressContact.IDefaultAddressView>(),
    DefaultAddressContact.IDefaultAddressPresenter {

    override fun loadDefaultAddress(nAddressListModelReq: NAddressListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(AddressApi::class.java)
            .loadAddressList(nAddressListModelReq)
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
                        val list = it.data.filter { it.is_default == 1 }
                        if (!list.isNullOrEmpty())
                            softView.get()?.loadDefaultAddressSuccess(list[0])
                    } else {
                        softView.get()?.loadDefaultAddressFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadDefaultAddressFail(throwable) }
            )
    }
}