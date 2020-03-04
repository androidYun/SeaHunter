package com.xhs.baselibrary.dialog.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xhs.baselibrary.R
import com.xhs.baselibrary.listener.OnItemClickListener
import kotlinx.android.synthetic.main.list_item_single_select.view.*

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 03/04/2019.
 * description:
 */
class SingleSelectAdapter(private val mList: List<String>, private val listener: OnItemClickListener) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectPosition: Int = -1



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return SingleViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_single_select, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        viewHolder.itemView.tvContent.text = mList[position]
        viewHolder.itemView.rbSelect.isChecked = selectPosition == position
        viewHolder.itemView.llSingle.setOnClickListener { view ->
            updateSelectView(viewHolder, view, position)
            listener.onItemClick(viewHolder, view, position)
        }
        viewHolder.itemView.rbSelect.setOnClickListener { view ->
            updateSelectView(viewHolder, view, position)
            listener.onItemClick(viewHolder, view, position)
        }
    }

    private fun updateSelectView(viewHolder: RecyclerView.ViewHolder, view: View?, position: Int) {
        if (selectPosition != position) {
            selectPosition = position
            notifyDataSetChanged()
        }
        listener.onItemClick(viewHolder, view, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    internal inner class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
