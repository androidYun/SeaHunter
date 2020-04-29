package com.sea.custom.presenter.category

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import com.sea.custom.api.CategoryApi


class CategoryPresenter : IPresenter<CategoryContact.ICategoryView>(),
    CategoryContact.ICategoryPresenter {
    override fun loadCategory(nCategoryModelReq: NCategoryModelReq) {
        RetrofitUtils.getRetrofit()
            .create(CategoryApi::class.java)
            .loadCategory(nCategoryModelReq)
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
                        softView.get()?.loadCategorySuccess(it.data)
                    } else {
                        softView.get()?.loadCategoryFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadCategoryFail(throwable) }
            )
    }
}