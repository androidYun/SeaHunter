package com.sea.custom.ui.delicacy

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.channel.NChannelItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class ToDayActivityAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(R.layout.item_delicacy_today_activity, mList) {
    override fun convert(helper: BaseViewHolder, item: NChannelItem) {
        helper?.setText(R.id.tvShopName, item.title)
        ImageLoader.loadImageWithUrl(
            helper.getView(R.id.ivSeaShop),
            Constants.baseUrl.plus(item.img_url)
        )
//        helper?.getView<LinearLayout>(R.id.lvShopDetail)?.setOnClickListener {
//            it.context.startActivity(Intent(it.context, ShopDetailActivity::class.java).apply {
//                putExtras(ShopDetailActivity.getInstance(item.id))
//            })
//        }
    }
}