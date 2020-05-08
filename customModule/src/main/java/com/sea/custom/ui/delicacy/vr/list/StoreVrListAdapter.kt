package com.sea.custom.ui.delicacy.vr.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.channel.NChannelItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class StoreVrListAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(
        R.layout.item_activity_store_vr_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem?) {
        helper?.setText(R.id.tvStoreName, item?.title ?: "")
        helper?.setText(R.id.tvStoreAddress, item?.address ?: "")
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivVrImage),
            Constants.baseUrl.plus(item?.img_url)
        )
    }
}