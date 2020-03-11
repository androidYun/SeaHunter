package com.sea.user.activity.mall.order.detail

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class MallOrderDetailAdapter(mList: List<NMallOrderDetailListItem>) :
    BaseQuickAdapter<NMallOrderDetailListItem, BaseViewHolder>(R.layout.item_mall_order_detail ,mList) {


    override fun convert(helper: BaseViewHolder, item: NMallOrderDetailListItem?) {
        when (getItemViewType(helper.layoutPosition)) {

        }
    }
}