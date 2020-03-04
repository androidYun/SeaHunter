package com.xhs.baselibrary.dialog.base;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.utils.DisplayUtils;
import com.xhs.baselibrary.utils.UIUtils;


/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 03/04/2019.
 * description:
 */
public class CommonDialog {
    public static class CommonBuilder extends BaseBuilder<CommonBuilder, CommonBuilder.CommonViewHolder> {
        private CharSequence message;

        public CommonBuilder(Context context) {
            super(context);
        }

        public CommonBuilder setMessage(CharSequence message) {
            if (message != null) {
                this.message = message;
                getViewHolder().tvMessage.setText(message);
            }
            return this;
        }

        public CommonBuilder setTitle(int resId) {
            getViewHolder().tvTitle.setText(resId);
            return this;
        }

        public CommonBuilder setTitle(CharSequence title) {
            this.title = title.toString();
            getViewHolder().tvTitle.setText(title);
            return this;
        }

        public CommonBuilder setTitle(CharSequence title, int color) {
            this.title = title;
            getViewHolder().tvTitle.setText(title);
            getViewHolder().tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public CommonBuilder setTitle(int resId, int color) {
            getViewHolder().tvTitle.setText(resId);
            getViewHolder().tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public CommonBuilder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
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

        public CommonBuilder setPositiveButton(int resId, final View.OnClickListener listener) {
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

        public CommonBuilder setPositiveButton(int resId, final View.OnClickListener listener, int color) {
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

        public CommonBuilder setNegativeButton(int resId, final View.OnClickListener listener) {
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

        public CommonBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
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

        public CommonBuilder setNegativeButton(CharSequence text, final View.OnClickListener listener, int color) {
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
            return LayoutInflater.from(mContext).inflate(R.layout.layout_common_dialog, null);
        }

        @Override
        public BaseViewHolder createViewHolder() {
            return new CommonViewHolder(getView());
        }

        @Override
        protected void requestLayout() {
            super.requestLayout();
            if ((title == null || title.toString().isEmpty()) && (message != null && !message.toString().isEmpty())) {
                getViewHolder().tvMessage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(90)));
            }
        }

        class CommonViewHolder extends BaseViewHolder {
            private TextView tvMessage;

            public CommonViewHolder(View view) {
                super(view);
                tvMessage = view.findViewById(R.id.tvMessage);
            }
        }

    }

}
