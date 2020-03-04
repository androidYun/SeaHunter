package com.xhs.baselibrary.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.utils.ScreenUtils;

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 08/04/2019.
 * description:
 */
public abstract class BaseBuilder<T extends BaseBuilder<T, K>, K extends BaseViewHolder> {
    public Context mContext;
    protected Dialog mDialog;
    private BaseViewHolder mViewBaseHolder;
    private View mView;
    protected CharSequence title, positive, negative;
    private int gravity = Gravity.CENTER;

    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;

    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;


    public BaseBuilder(Context context) {
        mContext = context;
        initView();
    }

    public BaseBuilder setLayout(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public T setWidth(int width) {
        this.width = width;
        return (T) this;
    }


    public T setWidthPercentage(double widthPercent) {
        this.width = (int) (ScreenUtils.getScreenWidth() * widthPercent);
        return (T) this;
    }

    public T setHeightPercentage(double heightPercent) {
        this.height = (int) (ScreenUtils.getScreenWidth() * heightPercent);
        return (T) this;
    }

    public T setHeight(int height) {
        this.height = height;
        return (T) this;
    }

    public T setGravity(int gravity) {
        this.gravity = gravity;
        return (T) this;
    }


    public T setCancelable(boolean flag) {
        mDialog.setCancelable(flag);
        return (T) this;
    }

    public T setCanceledOnTouchOutside(boolean flag) {
        mDialog.setCanceledOnTouchOutside(flag);
        return (T) this;
    }


    public Dialog getDialog() {
        return mDialog;
    }


    public Dialog create() {
        resetWindowLayout();
        requestLayout();
        return mDialog;
    }

    private void resetWindowLayout() {
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = width;
        lp.height = height;
        lp.gravity = gravity;
        mDialog.getWindow().setAttributes(lp);
    }

    protected void requestLayout() {
        if (negative == null || positive == null || negative.toString().isEmpty() || positive.toString().isEmpty()) {
            mViewBaseHolder.lineViewTwo.setVisibility(View.GONE);
        } else {
            mViewBaseHolder.lineViewTwo.setVisibility(View.VISIBLE);
        }
        if (title == null || title.toString().isEmpty()) {
            mViewBaseHolder.tvTitle.setVisibility(View.GONE);
            mViewBaseHolder.lineTimeView.setVisibility(View.GONE);
        } else {
            mViewBaseHolder.tvTitle.setVisibility(View.VISIBLE);
            mViewBaseHolder.lineTimeView.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void initView() {
        mDialog = new Dialog(mContext, R.style.baseDialogStyle);
        mView = getInflateView();
        mViewBaseHolder = createViewHolder();
        mDialog.setContentView(mView);
    }

    public abstract View getInflateView();

    protected K getViewHolder() {
        if (mViewBaseHolder == null) {
            mViewBaseHolder = createViewHolder();
        }
        return (K) mViewBaseHolder;
    }

    protected View getView() {
        if (mView == null) {
            mView = getInflateView();
        }
        return mView;
    }


    public abstract BaseViewHolder createViewHolder();

}
