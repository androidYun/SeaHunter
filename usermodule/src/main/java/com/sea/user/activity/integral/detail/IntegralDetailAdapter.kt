package com.sea.user.activity.integral.detail

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class IntegralDetailAdapter(mList: List<IntegralDetailItem>) :
    BaseQuickAdapter<IntegralDetailItem, BaseViewHolder>(
        R.layout.item_integral_detail_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: IntegralDetailItem) {
        helper?.setText(R.id.tvIntegralTime, item.add_time)
        if (item.point > 0) {
            helper?.setText(R.id.tvIntegral, "+${item.point}")
        } else {
            helper?.setText(R.id.tvIntegral, "${item.point}")
        }
        helper?.setText(R.id.tvIntegralType, item.title)
    }
}