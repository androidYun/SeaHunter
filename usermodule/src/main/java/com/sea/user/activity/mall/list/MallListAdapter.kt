package com.sea.user.activity.mall.list

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.detail.ShopDetailActivity

class MallListAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_mall_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MallListItem?) {
        helper?.setText(R.id.tvShopName, "")
        helper?.setText(R.id.tvShopDesc, "")
        helper?.setText(R.id.tvShopPrice, "")
        helper?.getView<LinearLayout>(R.id.lvShopDetail)?.setOnClickListener {

            it.context.startActivity(Intent(it.context, ShopDetailActivity::class.java))
        }
    }
}