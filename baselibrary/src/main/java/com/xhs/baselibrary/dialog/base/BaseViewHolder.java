package com.xhs.baselibrary.dialog.base;

import android.view.View;
import android.widget.TextView;

import com.xhs.baselibrary.R;

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 08/04/2019.
 * description:
 */
public class BaseViewHolder {
    public TextView tvTitle;
    public TextView tvPositiveButton, tvNegativeButton;
    public View lineViewTwo, lineTimeView;

    public BaseViewHolder(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvPositiveButton = view.findViewById(R.id.tvPositive);
        tvNegativeButton = view.findViewById(R.id.tvNegative);
        lineViewTwo = view.findViewById(R.id.view_line_two);
        lineTimeView = view.findViewById(R.id.view_title_line);
    }
}
