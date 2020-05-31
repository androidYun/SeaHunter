package com.sea.custom.ui.store

import android.content.Intent
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.ui.vr.VrDetailActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class StoreListAdapter(mList: List<StoreListItem>) :
    BaseQuickAdapter<StoreListItem, BaseViewHolder>(R.layout.item_store_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: StoreListItem) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivHead),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.setText(R.id.tvStoreName, item.title)
        helper?.setText(R.id.tvContact, item.mobile)
        helper?.setText(R.id.tvStoreAddress, item.address)
        helper?.addOnClickListener(R.id.tvLookVr)
        helper?.getView<TextView>(R.id.tvLookVr)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, VrDetailActivity::class.java).apply {
                putExtras(
                    VrDetailActivity.getInstance(item.vr_url)
                )
            })
        }
    }
}