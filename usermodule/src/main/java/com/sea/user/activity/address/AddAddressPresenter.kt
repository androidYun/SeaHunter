package com.sea.user.activity.address

import com.sea.user.api.AddressApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.base.example.ExampleApi
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class AddAddressPresenter : IPresenter<AddAddressContract.IAddAddressView>(), AddAddressContract.IAddAddressPresenter {

    override fun loadAddAddress( nAddressModelReq: NAddressModelReq) {
        RetrofitUtils.getRetrofit()
            .create(AddressApi::class.java)
            .loadAddAddress(nAddressModelReq)
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
                        softView.get()?.loadAddAddressSuccess()
                    } else {
                        softView.get()?.loadAddAddressFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadAddAddressFail(throwable) }
            )
    }

    override fun loadAddress(addressId: Int) {
        RetrofitUtils.getRetrofit()
            .create(AddressApi::class.java)
            .loadAddressDetail(addressId)
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
                        softView.get()?.loadAddressSuccess()
                    } else {
                        softView.get()?.loadAddressFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadAddAddressFail(throwable) }
            )
    }

    override fun modifyAddress(nAddressModelReq: NAddressModelReq) {
        RetrofitUtils.getRetrofit()
            .create(AddressApi::class.java)
            .modifyAddressDetail(nAddressModelReq)
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
                        softView.get()?.modifyAddressSuccess()
                    } else {
                        softView.get()?.modifyAddressFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.modifyAddressFail(throwable) }
            )
    }
}