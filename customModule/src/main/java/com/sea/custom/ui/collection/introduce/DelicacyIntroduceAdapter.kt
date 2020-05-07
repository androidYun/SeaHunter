package com.sea.custom.ui.collection.introduce

import android.widget.RadioButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.ui.collection.CollectionItem

class DelicacyIntroduceAdapter(mList: List<CollectionItem>) :
    BaseQuickAdapter<CollectionItem, BaseViewHolder>(
        R.layout.item_activity_delicacy_introduce_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: CollectionItem) {
        helper?.setText(R.id.tvDelicacyName, item.title)
        helper?.getView<RadioButton>(R.id.rgbPraise)?.text = item.good_count
        helper?.getView<RadioButton>(R.id.rgbComment)?.text = item.comment_count
        helper?.getView<RadioButton>(R.id.rgbCollection)?.text = item.collect_count
        helper?.getView<RadioButton>(R.id.rgbCollection)?.isChecked = true
        helper?.getView<RadioButton>(R.id.rgbForward)?.text = item.share_count
        helper?.getView<RadioButton>(R.id.rgbPraise)?.isEnabled = false
        helper?.getView<RadioButton>(R.id.rgbComment)?.isEnabled = false
        helper?.getView<RadioButton>(R.id.rgbCollection)?.isEnabled = false
        helper?.getView<RadioButton>(R.id.rgbForward)?.isEnabled = false
    }
}