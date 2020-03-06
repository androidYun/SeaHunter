package com.sea.user.activity.integral.detail

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class IntegralDetailAdapter(mList: List<IntegralDetailItem>) :
    BaseQuickAdapter<IntegralDetailItem, BaseViewHolder>(R.layout.item_integral_detail_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: IntegralDetailItem?) {
        helper?.setText(R.id.tvIntegralType, "")
        helper?.setText(R.id.tvIntegralTime, "")
        helper?.setText(R.id.tvIntegral, "")
        helper?.setText(R.id.tvIntegralType, "")
        ImageLoader.loadCircleImageView(helper?.getView(R.id.ivIntegralHead), "")
    }
}