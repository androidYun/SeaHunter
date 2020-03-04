package com.xhs.baselibrary.listener;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 08/04/2019.
 * description:
 */
public interface OnItemClickListener {
    void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position);
}
