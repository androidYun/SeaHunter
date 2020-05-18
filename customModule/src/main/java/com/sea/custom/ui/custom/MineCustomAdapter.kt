package com.sea.custom.ui.custom

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.holder.RecyclerItemNormalHolder
import com.sea.custom.model.VideoModel
import com.xhs.baselibrary.utils.UIUtils
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class MineCustomAdapter(mList: List<MineCustomItem>) :
    BaseQuickAdapter<MineCustomItem, BaseViewHolder>(R.layout.item_mine_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: MineCustomItem) {
        val recyclerItemViewHolder = helper as RecyclerItemNormalHolder
        recyclerItemViewHolder.recyclerBaseAdapter = this
        recyclerItemViewHolder.onBind(
            0,
            VideoModel(
                item.article?.fields?.video_src ?: "",
                item.article?.title ?: "",
                item.article?.img_url ?: ""
            )
        )
        helper?.setText(R.id.tvDelicacyName, item.article?.title)
        if (item.article?.status == 0) {
            helper?.setText(R.id.tvCustomStatusContent, "等待分配门店中")
            helper?.setTextColor(
                R.id.tvCustomStatusContent,
                UIUtils.getInstance().getColor(R.color.color_e31c1c)
            )
            helper?.setImageResource(R.id.ivCustomStatus, R.mipmap.icon_distribution)
        } else {
            helper?.setTextColor(
                R.id.tvCustomStatusContent,
                UIUtils.getInstance().getColor(R.color.color_1ca422)
            )
            helper?.setText(R.id.tvCustomStatusContent, "门店已完成联系")
            helper?.setImageResource(R.id.ivCustomStatus, R.mipmap.icon_complete)
        }

    }

    override fun createBaseViewHolder(view: View?): BaseViewHolder {
        return RecyclerItemNormalHolder(view?.context, view)
    }
}