package com.xhs.baselibrary.dialog;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.dialog.adapter.MultipleSelectAdapter;
import com.xhs.baselibrary.dialog.base.BaseBuilder;
import com.xhs.baselibrary.dialog.base.BaseViewHolder;
import com.xhs.baselibrary.utils.UIUtils;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 08/04/2019.
 * description:
 */
public class MultipleSelectionDialog {

    public static class MultipleSelectBuilder extends BaseBuilder<MultipleSelectBuilder,MultipleSelectBuilder.MultipleSelectViewHolder> {
        public MultipleSelectBuilder(Context context) {
            super(context);
        }

        private Set<Integer> mSelectedView = new HashSet<Integer>();

        public MultipleSelectBuilder setItemList(List<String> multipleList, int maxCount, MultipleSelectListener selectListener) {
            MultipleSelectViewHolder mViewHolder = getViewHolder();
            MultipleSelectAdapter multipleSelectAdapter = new MultipleSelectAdapter(multipleList);
            mViewHolder.tagFlowLayout.setMaxSelectCount(maxCount);
            mViewHolder.tagFlowLayout.setAdapter(multipleSelectAdapter);
            mViewHolder.tagFlowLayout.setOnSelectListener((selectPosSet) -> {
                        mSelectedView = selectPosSet;
                        if (selectListener != null) {
                            selectListener.selectSuccess(mSelectedView);
                        }
                    }
            );
            return this;
        }

        public MultipleSelectBuilder setTitle(int resId) {
            this.title = UIUtils.getInstance().getString(resId);
            getViewHolder().tvTitle.setText(resId);
            return this;
        }

        public MultipleSelectBuilder setTitle(CharSequence title) {
            this.title = title.toString();
            getViewHolder().tvTitle.setText(title);
            return this;
        }

        public MultipleSelectBuilder setTitle(CharSequence title, int color) {
            this.title = title;
            getViewHolder().tvTitle.setText(title);
            getViewHolder().tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public MultipleSelectBuilder setTitle(int resId, int color) {
            this.title = UIUtils.getInstance().getString(resId);
            getViewHolder().tvTitle.setText(resId);
            getViewHolder().tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public MultipleSelectBuilder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
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

        public MultipleSelectBuilder setPositiveButton(int resId, final View.OnClickListener listener) {
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

        public MultipleSelectBuilder setPositiveButton(int resId, final MultipleSelectListener listener) {
            this.positive = UIUtils.getInstance().getString(resId);
            getViewHolder().tvPositiveButton.setVisibility(View.VISIBLE);
            getViewHolder().tvPositiveButton.setText(positive);
            getViewHolder().tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null && !mSelectedView.isEmpty()) {
                    listener.selectSuccess(mSelectedView);
                }
            });
            return this;
        }

        public MultipleSelectBuilder setPositiveButton(String positive, final MultipleSelectListener listener) {
            this.positive = positive;
            getViewHolder().tvPositiveButton.setVisibility(View.VISIBLE);
            getViewHolder().tvPositiveButton.setText(positive);
            getViewHolder().tvPositiveButton.setOnClickListener(view -> {
                mDialog.dismiss();
                if (listener != null && !mSelectedView.isEmpty()) {
                    listener.selectSuccess(mSelectedView);
                }
            });
            return this;
        }

        public MultipleSelectBuilder setPositiveButton(int resId, final View.OnClickListener listener, int color) {
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

        public MultipleSelectBuilder setNegativeButton(int resId, final View.OnClickListener listener) {
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

        public MultipleSelectBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
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

        public MultipleSelectBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener, int color) {
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
            return LayoutInflater.from(mContext).inflate(R.layout.dialog_multiple_select, null);
        }

        @Override
        public BaseViewHolder createViewHolder() {
            return new MultipleSelectViewHolder(getView());
        }

        class MultipleSelectViewHolder extends BaseViewHolder {
            private TagFlowLayout tagFlowLayout;

            private MultipleSelectViewHolder(View view) {
                super(view);
                tagFlowLayout = view.findViewById(R.id.flow_layout);
            }
        }
    }

    public interface MultipleSelectListener {

        void selectSuccess(Set<Integer> selectPosSet);
    }

}
