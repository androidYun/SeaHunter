package com.xhs.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.dialog.adapter.SingleSelectAdapter;
import com.xhs.baselibrary.dialog.base.BaseBuilder;
import com.xhs.baselibrary.dialog.base.BaseViewHolder;
import com.xhs.baselibrary.utils.UIUtils;

import java.util.List;

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 08/04/2019.
 * description:
 */
public class SingleSelectDialog {

    public static class SingleSelectBuilder extends BaseBuilder<SingleSelectBuilder,SingleSelectBuilder.SingleSelectViewHolder> {


        public SingleSelectBuilder(Context context) {
            super(context);
        }

        /**
         * 选择的位置
         */
        private int selectPosition = -1;

        public SingleSelectBuilder setItemList(List<String> singleList, SingleSelectListener selectListener) {
            SingleSelectAdapter singleSelectAdapter = new SingleSelectAdapter(singleList, (viewHolder, view, position) -> {
                this.selectPosition = position;
                if (selectListener != null && getDialog() != null) {
                    selectListener.selectSuccess(getDialog(), position);
                }
            });
            getViewHolder().rvSingleSelect.setLayoutManager(new LinearLayoutManager(mContext));
            getViewHolder().rvSingleSelect.setAdapter(singleSelectAdapter);
            return this;
        }

        public SingleSelectBuilder setTitle(int resId) {
            getViewHolder().tvTitle.setText(resId);
            return this;
        }

        public SingleSelectBuilder setTitle(CharSequence title) {
            this.title = title.toString();
            getViewHolder().tvTitle.setText(title);
            return this;
        }

        public SingleSelectBuilder setTitle(CharSequence title, int color) {
            this.title = title;
            getViewHolder().tvTitle.setText(title);
            getViewHolder().tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public SingleSelectBuilder setTitle(int resId, int color) {
            getViewHolder().tvTitle.setText(resId);
            getViewHolder().tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public SingleSelectBuilder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
            this.positive = text;
            getViewHolder().tvPositiveButton.setVisibility(View.VISIBLE);
            getViewHolder().tvPositiveButton.setText(text);
            getViewHolder().tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SingleSelectBuilder setPositiveButton(int resId, final View.OnClickListener listener) {
            this.positive = UIUtils.getInstance().getString(resId);
            getViewHolder().tvPositiveButton.setVisibility(View.VISIBLE);
            getViewHolder().tvPositiveButton.setText(positive);
            getViewHolder().tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SingleSelectBuilder setPositiveButton(int resId, final View.OnClickListener listener, int color) {
            this.positive = UIUtils.getInstance().getString(resId);
            getViewHolder().tvPositiveButton.setVisibility(View.VISIBLE);
            getViewHolder().tvPositiveButton.setText(positive);
            getViewHolder().tvPositiveButton.setTextColor(ContextCompat.getColor(mContext, color));
            getViewHolder().tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SingleSelectBuilder setPositiveButton(CharSequence text, final SingleSelectListener listener) {
            this.positive = text;
            getViewHolder().tvPositiveButton.setVisibility(View.VISIBLE);
            getViewHolder().tvPositiveButton.setText(text);
            getViewHolder().tvPositiveButton.setOnClickListener(view -> {
                if (listener != null && getDialog() != null) {
                    listener.selectSuccess(getDialog(), selectPosition);
                }
            });
            return this;
        }

        public SingleSelectBuilder setNegativeButton(int resId, final View.OnClickListener listener) {
            this.negative = UIUtils.getInstance().getString(resId);
            getViewHolder().tvNegativeButton.setVisibility(View.VISIBLE);
            getViewHolder().tvNegativeButton.setText(negative);
            getViewHolder().tvNegativeButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        public SingleSelectBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
            this.negative = text;
            getViewHolder().tvNegativeButton.setVisibility(View.VISIBLE);
            getViewHolder().tvNegativeButton.setText(text);
            getViewHolder().tvNegativeButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }


        public SingleSelectBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener, int color) {
            this.negative = text;
            getViewHolder().tvNegativeButton.setVisibility(View.VISIBLE);
            getViewHolder().tvNegativeButton.setText(text);
            getViewHolder().tvNegativeButton.setTextColor(ContextCompat.getColor(mContext, color));
            getViewHolder().tvNegativeButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            return this;
        }

        @Override
        public View getInflateView() {
            return LayoutInflater.from(mContext).inflate(R.layout.dialog_single_select, null);
        }

        @Override
        public BaseViewHolder createViewHolder() {
            return new SingleSelectViewHolder(getView());
        }

        @Override
        public Dialog create() {
            return super.create();
        }

        class SingleSelectViewHolder extends BaseViewHolder {
            RecyclerView rvSingleSelect;

            public SingleSelectViewHolder(View view) {
                super(view);
                rvSingleSelect = view.findViewById(R.id.rvSingleSelect);
            }
        }
    }

    public interface SingleSelectListener {

        void selectSuccess(Dialog alertDialog, int position);
    }

}
