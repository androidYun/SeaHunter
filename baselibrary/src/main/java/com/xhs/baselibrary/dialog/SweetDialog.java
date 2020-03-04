package com.xhs.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.dialog.adapter.SweetListAdapter;
import com.xhs.baselibrary.utils.UIUtils;

import java.util.List;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 09/04/2019.
 * description:
 */
public class SweetDialog {


    public static class SweetBuilder {
        private Context mContext;

        protected Dialog mDialog;

        private SweetViewHolder sweetViewHolder;

        protected CharSequence positive, negative;

        private int selectPosition = -1;

        private int gravity = Gravity.BOTTOM;

        public SweetBuilder(Context mContext) {
            this.mContext = mContext;
            initView();
        }


        public SweetBuilder setItemList(List<String> singleList, SweetSingleSelectListener selectListener) {
            SweetListAdapter sweetAdapter = new SweetListAdapter(R.layout.item_sweet_dialog_layout, singleList);
            sweetAdapter.setOnItemClickListener((adapter, view, position) -> {
                this.selectPosition = position;
                if (selectListener != null) {
                    selectListener.selectSuccess(mDialog,position);
                }
            });
            sweetViewHolder.rvItem.setLayoutManager(new LinearLayoutManager(mContext));
            sweetViewHolder.rvItem.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            sweetViewHolder.rvItem.setAdapter(sweetAdapter);
            return this;
        }

        public SweetBuilder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
            this.positive = text;
            sweetViewHolder.tvPositiveButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvPositiveButton.setText(text);
            sweetViewHolder.tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SweetBuilder setPositiveButton(int resId, final View.OnClickListener listener) {
            this.positive = UIUtils.getInstance().getString(resId);
            sweetViewHolder.tvPositiveButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvPositiveButton.setText(positive);
            sweetViewHolder.tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SweetBuilder setPositiveButton(int resId, final View.OnClickListener listener, int color) {
            this.positive = UIUtils.getInstance().getString(resId);
            sweetViewHolder.tvPositiveButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvPositiveButton.setText(positive);
            sweetViewHolder.tvPositiveButton.setTextColor(ContextCompat.getColor(mContext, color));
            sweetViewHolder.tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SweetBuilder setPositiveButton(String text, final SweetSingleSelectListener listener) {
            this.positive = text;
            sweetViewHolder.tvPositiveButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvPositiveButton.setText(text);
            sweetViewHolder.tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null && selectPosition != -1) {
                    listener.selectSuccess(mDialog,selectPosition);
                }
            });
            return this;
        }

        public SweetBuilder setNegativeButton(int resId, final View.OnClickListener listener) {
            this.negative = UIUtils.getInstance().getString(resId);
            sweetViewHolder.tvNegativeButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvNegativeButton.setText(negative);
            sweetViewHolder.tvNegativeButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SweetBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
            this.negative = text;
            sweetViewHolder.tvNegativeButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvNegativeButton.setText(text);
            sweetViewHolder.tvNegativeButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }


        public SweetBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener, int color) {
            this.negative = text;
            sweetViewHolder.tvNegativeButton.setVisibility(View.VISIBLE);
            sweetViewHolder.tvNegativeButton.setText(text);
            sweetViewHolder.tvNegativeButton.setTextColor(ContextCompat.getColor(mContext, color));
            sweetViewHolder.tvNegativeButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        private void initView() {
            mDialog = new Dialog(mContext, R.style.baseDialogStyle);
            View inflateView = getInflateView();
            sweetViewHolder = getViewHolder(inflateView);
            mDialog.setContentView(inflateView);
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.width = (int) (dm.widthPixels * 0.9);
            mDialog.getWindow().setAttributes(lp);
        }


        public View getInflateView() {
            return LayoutInflater.from(mContext).inflate(R.layout.dialog_sweet_list_layout, null);
        }


        public SweetViewHolder getViewHolder(View mView) {
            return new SweetViewHolder(mView);
        }

        public SweetBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }


        public SweetBuilder setCancelable(boolean flag) {
            mDialog.setCancelable(flag);
            return this;
        }

        public SweetBuilder setCanceledOnTouchOutside(boolean flag) {
            mDialog.setCanceledOnTouchOutside(flag);
            return this;
        }

        public Dialog create() {
            mDialog.getWindow().setGravity(gravity);
            return mDialog;
        }

    }

    static class SweetViewHolder {

        TextView tvPositiveButton, tvNegativeButton;

        RecyclerView rvItem;

        public SweetViewHolder(View view) {
            tvPositiveButton = view.findViewById(R.id.tvPositive);
            tvNegativeButton = view.findViewById(R.id.tvNegative);
            rvItem = view.findViewById(R.id.rvItem);
        }
    }

    public interface SweetSingleSelectListener {

        void selectSuccess(Dialog dialog, int position);
    }

}
