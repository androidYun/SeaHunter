package com.sea.custom.ui.delicacy.vr.list

import android.content.Intent
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.ui.store.StoreListItem
import com.sea.custom.ui.vr.VrDetailActivity
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class StoreVr2ListAdapter(mList: List<StoreListItem>) :
    BaseQuickAdapter<StoreListItem, BaseViewHolder>(
        R.layout.item_activity_store_vr_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: StoreListItem?) {
        helper?.setText(R.id.tvStoreName, item?.title ?: "")
        helper?.setText(R.id.tvStoreAddress, item?.address ?: "")
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivVrImage),
            Constants.baseUrl.plus(item?.img_url)
        )
        helper?.getView<LinearLayout>(R.id.lvVr)?.setOnClickListener {
            it.context.startActivity(Intent(it.context, VrDetailActivity::class.java).apply {
                putExtras(
                    VrDetailActivity.getInstance(item?.vr_url ?: "")
                )
            })
        }
    }
}