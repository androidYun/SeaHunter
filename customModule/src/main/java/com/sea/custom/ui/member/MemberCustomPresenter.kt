package com.sea.custom.ui.member

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class MemberCustomPresenter : IPresenter<MemberCustomContact.IMemberCustomView>(),
    MemberCustomContact.IMemberCustomPresenter {
    override fun loadMemberCustom(nMemberCustomModelReq: NMemberCustomModelReq) {
//        RetrofitUtils.getRetrofit()
//            .create(MemberCustomApi::class.java)
//            .loadMemberCustom(nMemberCustomModelReq)
//            .compose(RxUtils.getSchedulerTransformer())
//            .compose(RxUtils.bindToLifecycle(softView.get()))
//            .doOnSubscribe { disposable ->
//                addDisposable(disposable)
//                softView.get()?.showLoading()
//            }.doFinally {
//                softView.get()?.hideLoading()
//                onStop()
//            }
//            .subscribe(
//                {
//                    if (it.code == 200) {
//                        softView.get()?.loadMemberCustomSuccess(it.data.mList, it.data.totalCount)
//                    } else {
//                        softView.get()?.loadMemberCustomFail(Throwable(it.msg))
//                    }
//                    //这里面是回调成功的方法
//                }, { throwable -> softView.get()?.loadMemberCustomFail(throwable) }
//            )
    }
}