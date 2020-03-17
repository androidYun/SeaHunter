package com.sea.user.activity.mall.list

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.activity.mall.detail.ShopDetailActivity
import com.sea.user.common.Constants
import com.sea.user.presenter.sea.mall.MallListItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class MallListAdapter(mList: List<MallListItem>) :
    BaseQuickAdapter<MallListItem, BaseViewHolder>(R.layout.item_mall_list_layout, mList) {
    override fun convert(helper: BaseViewHolder, item: MallListItem) {
        helper?.setText(R.id.tvShopName, item.title)
        helper?.setText(R.id.tvShopDesc, item.content)
        helper?.setText(R.id.tvShopPrice, "￥${item.sell_price}")
        ImageLoader.loadImageWithUrl(
            helper.getView(R.id.ivSeaShop),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.getView<LinearLayout>(R.id.lvShopDetail)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, ShopDetailActivity::class.java).apply {
                putExtras(ShopDetailActivity.getInstance(item.id))
            })
        }
    }
}