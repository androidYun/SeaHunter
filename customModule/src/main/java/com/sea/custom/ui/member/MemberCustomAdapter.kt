package com.sea.custom.ui.member

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.holder.RecyclerItemNormalHolder
import com.sea.custom.model.VideoModel
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.ui.member.detail.MemberCustomDetailActivity
import com.sea.custom.web.ChannelWebDetailActivity
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.utils.UIUtils

class MemberCustomAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(R.layout.item_member_custom_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem) {
        val recyclerItemViewHolder = helper as RecyclerItemNormalHolder
        recyclerItemViewHolder.recyclerBaseAdapter = this
        recyclerItemViewHolder.onBind(
            helper.layoutPosition,
            VideoModel(item.video_src ?: "", item.title ?: "", item.img_url ?: "")
        )
        helper?.setText(R.id.tvTitle, item?.title ?: "")
        if (UserInformSpUtils.getUserInformModel().group_id == 1) {
            helper.getView<ImageView>(R.id.ivCustomStatus)
                .setImageResource(R.mipmap.vip_registered)//普通
            helper.getView<TextView>(R.id.tvCustomMember)
                .setTextColor(UIUtils.getInstance().getColor(R.color.custom_theme_color))
        } else if (UserInformSpUtils.getUserInformModel().group_id == 2) {
            helper.getView<ImageView>(R.id.ivCustomStatus)
                .setImageResource(R.mipmap.vip_ordinary)//Vip
            helper.getView<TextView>(R.id.tvCustomMember)
                .setTextColor(UIUtils.getInstance().getColor(R.color.custom_theme_color))
        } else if (UserInformSpUtils.getUserInformModel().group_id == 3) {
            helper.getView<ImageView>(R.id.ivCustomStatus)
                .setImageResource(R.mipmap.vip_senior)//Vip
            helper.getView<TextView>(R.id.tvCustomMember)
                .setTextColor(UIUtils.getInstance().getColor(R.color.color_774e2a))
        }
        helper?.addOnClickListener(R.id.tvCustomMember)
        helper?.getView<LinearLayout>(R.id.lvMember)?.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    MemberCustomDetailActivity::class.java
                ).apply {
                    putExtras(
                        MemberCustomDetailActivity.getInstance(nChannelItem = item)
                    )
                })
        }
    }

    override fun createBaseViewHolder(view: View?): BaseViewHolder {
        return RecyclerItemNormalHolder(view?.context, view)
    }
}