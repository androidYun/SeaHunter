package com.sea.custom.ui.delicacy.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.ui.delicacy.NDelicacyKindItem

class DelicacyKindAdapter(mList: List<NDelicacyKindItem>) :
    BaseQuickAdapter<NDelicacyKindItem, BaseViewHolder>(
        R.layout.item_delicacy_kind_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NDelicacyKindItem) {
        helper?.getView<ImageView>(R.id.ivDelicacyKind)?.setImageResource(item.resId)
        helper?.setText(R.id.tvKindTitle, item.delicacyKindTitle)
    }
}