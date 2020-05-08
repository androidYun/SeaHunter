package com.sea.custom.ui.delicacy.introduce

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.channel.NChannelItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class DelicacyIntroduceAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(
        R.layout.item_delicacy_introduce_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem?) {
        helper?.setText(R.id.tvFishName, item?.title ?: "")
        helper?.setText(R.id.tvIntroduce, item?.zhaiyao ?: "")
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivFish),
            Constants.baseUrl.plus(item?.img_url ?: "")
        )
    }
}