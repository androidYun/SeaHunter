package com.sea.user.activity.mall.detail

import android.widget.TextView
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.utils.UIUtils

class SelectSpcAdapter(val mList: List<ShopSpecItemSon>) :
    BaseQuickAdapter<ShopSpecItemSon, BaseViewHolder>(R.layout.item_select_spc_layout, mList) {

    private var selectPosition = 0
    override fun convert(helper: BaseViewHolder, item: ShopSpecItemSon?) {
        helper?.setText(R.id.tvSpec, item?.title)
        if (selectPosition == helper?.layoutPosition) {
            mData[selectPosition].isSelect = true
            helper.getView<TextView>(R.id.tvSpec)
                .setTextColor(UIUtils.getInstance().getColor(R.color.white))
            helper.getView<CardView>(R.id.cardView)
                .setCardBackgroundColor(UIUtils.getInstance().getColor(R.color.user_theme_color))
        } else {
            mData[helper?.layoutPosition].isSelect = false
            helper.getView<TextView>(R.id.tvSpec)
                .setTextColor(UIUtils.getInstance().getColor(R.color.black))
            helper.getView<CardView>(R.id.cardView)
                .setCardBackgroundColor(UIUtils.getInstance().getColor(R.color.color_f7f7f7))
        }
        helper.getView<CardView>(R.id.cardView).setOnClickListener {
            selectPosition = helper.layoutPosition
            notifyDataSetChanged()
        }
    }
}