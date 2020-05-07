package com.sea.custom.ui.entertainment.list

import android.widget.RadioButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.presenter.channel.NChannelItem

class EntertainmentListAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(
        R.layout.item_fragment_entertainment_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem?) {
        helper?.setText(R.id.tvVideoName, item?.title ?: "")
        helper?.getView<RadioButton>(R.id.rgbPraise)?.text = "${item?.zan}"
        helper?.getView<RadioButton>(R.id.rgbComment)?.text = "${item?.comment_num}"
        helper?.getView<RadioButton>(R.id.rgbCollection)?.text = "${item?.collect_num}"
        helper?.getView<RadioButton>(R.id.rgbForward)?.text = "${item?.share}"
    }
}