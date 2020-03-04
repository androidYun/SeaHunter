package com.xhs.baselibrary.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.SoftReference

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 29/04/2019.
 * description:
 */
abstract class IPresenter<T : IBaseView> {
   protected lateinit var softView: SoftReference<T>

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun attachView(view: T) {
        softView = SoftReference(view)
    }


    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    fun onStop() {
        if (compositeDisposable.size() > 0) {
            compositeDisposable.clear()
        }
    }

}