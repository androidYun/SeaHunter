package com.sea.custom.ui.delicacy.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.ui.delicacy.NDelicacyTypeItem

class DelicacyTypeAdapter(mList: List<NDelicacyTypeItem>) :
    BaseQuickAdapter<NDelicacyTypeItem, BaseViewHolder>(
        R.layout.item_delicacy_type_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NDelicacyTypeItem) {
        helper?.getView<ImageView>(R.id.ivDelicacyType)?.setImageResource(item.resId)
        helper?.setText(R.id.tvDelicacyContent, item.delicacyTypeTitle)
        helper?.setText(R.id.tvDelicacyType, item.delicacyTypeContent)
    }
}