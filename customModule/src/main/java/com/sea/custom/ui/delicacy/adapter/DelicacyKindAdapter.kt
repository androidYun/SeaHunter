package com.sea.custom.ui.delicacy.adapter

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.ui.delicacy.store.StoreDelicacyActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class DelicacyKindAdapter(mList: List<NCategoryItem>) :
    BaseQuickAdapter<NCategoryItem, BaseViewHolder>(
        R.layout.item_delicacy_kind_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NCategoryItem) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivDelicacyKind),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.setText(R.id.tvKindTitle, item.title)
        helper?.getView<LinearLayout>(R.id.lvShopDetail)?.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    StoreDelicacyActivity::class.java
                ).apply { putExtras(StoreDelicacyActivity.getInstance(item.id)) })
        }
    }
}