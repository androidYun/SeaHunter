package com.xhs.baselibrary.dialog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xhs.baselibrary.R;

import java.util.List;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 09/04/2019.
 * description:
 */
public class SweetListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SweetListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    private int selectPosition = -1;

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvItem, item);
        helper.getView(R.id.tvItem).setSelected(selectPosition == helper.getAdapterPosition());
        helper.getView(R.id.tvItem).setOnClickListener((view) -> {
                    getOnItemClickListener().onItemClick(this, view, helper.getAdapterPosition());
                    selectPosition = helper.getAdapterPosition();
                    notifyDataSetChanged();
                }
        );
    }
}
