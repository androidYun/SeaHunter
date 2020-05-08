package com.sea.custom.ui.member

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.holder.RecyclerItemNormalHolder
import com.sea.custom.model.VideoModel
import com.sea.custom.presenter.channel.NChannelItem
import com.xhs.baselibrary.utils.UIUtils

class MemberCustomAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(R.layout.item_member_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem) {
        val recyclerItemViewHolder = helper as RecyclerItemNormalHolder
        recyclerItemViewHolder.recyclerBaseAdapter = this
        recyclerItemViewHolder.onBind(
            0,
            VideoModel(item.video_src ?: "", item.title ?: "", item.img_url ?: "")
        )
        helper?.setText(R.id.tvTitle, item?.title ?: "")
        if (item.address.isNullOrBlank()) {
            helper.getView<ImageView>(R.id.ivCustomStatus)
                .setImageResource(R.mipmap.vip_senior)//普通
            helper.getView<TextView>(R.id.tvCustomMember)
                .setTextColor(UIUtils.getInstance().getColor(R.color.color_774e2a))
        } else {
            helper.getView<ImageView>(R.id.ivCustomStatus)
                .setImageResource(R.mipmap.vip_ordinary)//普通
            helper.getView<TextView>(R.id.tvCustomMember)
                .setTextColor(UIUtils.getInstance().getColor(R.color.custom_theme_color))
        }
    }

    override fun createBaseViewHolder(view: View?): BaseViewHolder {
        return RecyclerItemNormalHolder(view?.context, view)
    }
}