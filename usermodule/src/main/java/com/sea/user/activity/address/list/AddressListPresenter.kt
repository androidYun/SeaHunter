package com.sea.user.activity.address.list

import com.sea.user.api.AddressApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class AddressListPresenter : IPresenter<AddressListContact.IAddressListView>(),
    AddressListContact.IAddressListPresenter {
    override fun loadAddressList(nAddressListModelReq: NAddressListModelReq) {
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
                    if (it.code == 0) {
                        softView.get()?.loadAddressListSuccess(it.data)
                    } else {
                        softView.get()?.loadAddressListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadAddressListFail(throwable) }
            )
    }
}