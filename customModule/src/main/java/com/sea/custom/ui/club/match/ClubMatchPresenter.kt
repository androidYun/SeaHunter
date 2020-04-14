package com.sea.custom.ui.club.match

import com.sea.custom.api.ClubApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ClubMatchPresenter : IPresenter<ClubMatchContact.IClubMatchView>(),
    ClubMatchContact.IClubMatchPresenter {
    override fun loadClubMatch(nClubMatchModelReq: NClubMatchModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ClubApi::class.java)
            .loadClubMatch(nClubMatchModelReq)
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
                        softView.get()?.loadClubMatchSuccess(it.data.mList, it.totalCount)
                    } else {
                        softView.get()?.loadClubMatchFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadClubMatchFail(throwable) }
            )
    }
}