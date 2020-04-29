package com.sea.custom.ui.club

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.ClubApi


class ClubPresenter : IPresenter<ClubContact.IClubView>(), ClubContact.IClubPresenter {
    override fun loadClub(nClubModelReq: NClubModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ClubApi::class.java)
            .loadClub(nClubModelReq)
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
                        softView.get()?.loadClubSuccess(it.data)
                    } else {
                        softView.get()?.loadClubFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadClubFail(throwable) }
            )
        softView.get()?.loadClubSuccess(listOf(NClubActivityItem(), NClubActivityItem()))
    }
}