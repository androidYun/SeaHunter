package com.sea.user.activity.mall.order.list

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ShopOrderListPresenter : IPresenter<ShopOrderListContact.IShopOrderListView>(),
    ShopOrderListContact.IShopOrderListPresenter {
    override fun loadShopOrderList(nShopOrderListModelReq: NShopOrderListModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopOrderListApi::class.java)
            .loadShopOrderList(nShopOrderListModelReq)
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
                        softView.get()?.loadShopOrderListSuccess(it.data.mList, it.data.totalCount)
                    } else {
                        softView.get()?.loadShopOrderListFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopOrderListFail(throwable) }
            )
    }
}