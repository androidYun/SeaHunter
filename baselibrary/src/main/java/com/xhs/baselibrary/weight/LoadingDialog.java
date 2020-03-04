package com.xhs.baselibrary.weight;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xhs.baselibrary.R;


/**
 * Created by zhf on 2018/10/19 0019.
 */

public class LoadingDialog extends Dialog {
    boolean isCancelable=true;
    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.dialog);
        init();
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.layout_loading,null);
        Window window=getWindow();
        setContentView(view);
        window.setGravity(Gravity.CENTER);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawableResource(R.color.base_transparent);
        setCancelable(isCancelable);
    }
}
