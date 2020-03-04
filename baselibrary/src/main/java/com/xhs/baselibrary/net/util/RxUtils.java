package com.xhs.baselibrary.net.util;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xhs.baselibrary.base.IBaseView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhf on 2018/10/12 0012.
 */

public class RxUtils {
    
    public static <T> LifecycleTransformer<T> bindToLifecycle(IBaseView view) {
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindToLifecycle();
        } else if (view instanceof RxFragment) {
            return ((RxFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity_test or fragment");
        }
    }

    public static <T> ObservableTransformer<T, T> getSchedulerTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
