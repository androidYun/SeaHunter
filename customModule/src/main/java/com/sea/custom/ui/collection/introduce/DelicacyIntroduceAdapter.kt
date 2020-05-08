package com.sea.custom.ui.collection.introduce

import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.holder.RecyclerItemNormalHolder
import com.sea.custom.model.VideoModel
import com.sea.custom.ui.collection.CollectionItem


class DelicacyIntroduceAdapter(mList: List<CollectionItem>) :
    BaseQuickAdapter<CollectionItem, BaseViewHolder>(
        R.layout.item_activity_delicacy_introduce_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: CollectionItem) {
        val recyclerItemViewHolder = helper as RecyclerItemNormalHolder
        recyclerItemViewHolder.recyclerBaseAdapter = this
        recyclerItemViewHolder.onBind(0, VideoModel(item.video_src, item.title,item.img_url))
        helper?.setText(R.id.tvDelicacyName, item.title)
        helper?.getView<RadioButton>(R.id.rgbPraise)?.text = "${item.zan}"
        helper?.getView<RadioButton>(R.id.rgbComment)?.text = "${item.comment_num}"
        helper?.getView<RadioButton>(R.id.rgbCollection)?.text = "${item.collect_num}"
        helper?.getView<RadioButton>(R.id.rgbCollection)?.isChecked = true
        helper?.getView<RadioButton>(R.id.rgbForward)?.text = "${item.share}"
        helper?.getView<RadioButton>(R.id.rgbPraise)?.isEnabled = false
        helper?.getView<RadioButton>(R.id.rgbComment)?.isEnabled = false
        helper?.getView<RadioButton>(R.id.rgbCollection)?.isEnabled = false
        helper?.getView<RadioButton>(R.id.rgbForward)?.isEnabled = false
    }


    override fun createBaseViewHolder(view: View?): BaseViewHolder {
        return RecyclerItemNormalHolder(view?.context, view)
    }
}