package com.xhs.baselibrary.dialog.adapter;

import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xhs.baselibrary.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @ author guiyun.li
 * @ email xyz_6776.@163.com
 * @ date 08/04/2019.
 * description:
 */
public class MultipleSelectAdapter extends TagAdapter<String> {
    public MultipleSelectAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_multiple_select_layout, parent, false);
        TextView tvMultiple = inflateView.findViewById(R.id.tvMultiple);
        tvMultiple.setText(s);
        return inflateView;
    }

    @Override
    public void unSelected(int position, View view) {
        super.unSelected(position, view);
        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.base_shape_multiple_normal_bg));
        ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.base_color_50));
    }

    @Override
    public void onSelected(int position, View view) {
        super.onSelected(position, view);
        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.base_shape_multiple_select_bg));
        ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.base_color_theme));
    }
}
