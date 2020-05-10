package com.sea.custom.ui.custom

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.xhs.baselibrary.utils.UIUtils
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class MineCustomAdapter(mList: List<MineCustomItem>) :
    BaseQuickAdapter<MineCustomItem, BaseViewHolder>(R.layout.item_mine_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MineCustomItem) {
//        helper?.setText(R.id.tvDelicacyName, item.title)
//        ImageLoader.loadImageWithUrl(
//            helper?.getView(R.id.ivHead),
//            Constants.baseUrl.plus(item.img_url)
//        )
//        if (item.state == 1) {
//            helper?.setText(R.id.tvCustomStatusContent, "等待分配门店中")
//            helper?.setTextColor(R.id.tvCustomStatusContent, UIUtils.getInstance().getColor(R.color.color_e31c1c))
//          //  helper?.setText(R.id.ivCustomStatus, item.title)
//        } else {
//            helper?.setTextColor(R.id.tvCustomStatusContent, UIUtils.getInstance().getColor(R.color.color_1ca422))
//            helper?.setText(R.id.tvCustomStatusContent, "门店已完成联系")
//            //helper?.setText(R.id.ivCustomStatus, item.title)
//        }

    }
}