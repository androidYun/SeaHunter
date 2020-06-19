package com.sea.custom.ui.make.list

import android.content.Intent
import android.view.View
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.holder.RecyclerItemNormalHolder
import com.sea.custom.model.VideoModel
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.ui.delicacy.comment.DelicacyCommentActivity

class DelicacyMakeListAdapter(mList: List<NChannelItem>) :
    BaseQuickAdapter<NChannelItem, BaseViewHolder>(
        R.layout.item_delicacy_make_list_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NChannelItem?) {
        val recyclerItemViewHolder = helper as RecyclerItemNormalHolder
        recyclerItemViewHolder.recyclerBaseAdapter = this
        recyclerItemViewHolder.onBind(
            helper.layoutPosition,
            VideoModel(item?.video_src ?: "", item?.title ?: "", item?.img_url ?: "")
        )
        helper?.setText(R.id.tvDelicacyName, item?.title)
        helper?.getView<CheckBox>(R.id.rgbPraise)?.text = "${item?.zan}"
        helper?.getView<CheckBox>(R.id.rgbComment)?.text = "${item?.comment_num}"
        helper?.getView<CheckBox>(R.id.rgbCollection)?.text = "${item?.collect_num}"
        helper?.getView<CheckBox>(R.id.rgbForward)?.text = "${item?.share}"
        helper?.getView<CheckBox>(R.id.rgbCollection)?.isChecked = item?.is_collect ?: false
        helper?.getView<CheckBox>(R.id.rgbPraise)?.isChecked = item?.is_zan ?: false
        helper?.getView<CheckBox>(R.id.rgbPraise)?.isEnabled = item?.is_zan != true//如果点过赞  就不能点了
        helper?.getView<CheckBox>(R.id.rgbComment)?.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    DelicacyCommentActivity::class.java
                ).apply {
                    putExtras(
                        DelicacyCommentActivity.getInstance(
                            ChannelEnum.food.name,
                            item?.id ?: -1
                        )
                    )
                })
        }
        helper.addOnClickListener(R.id.rgbCollection)
        helper.addOnClickListener(R.id.tvDelicacyStatus)
        helper.addOnClickListener(R.id.rgbForward)
        helper.addOnClickListener(R.id.rgbPraise)
    }

    override fun createBaseViewHolder(view: View?): BaseViewHolder {
        return RecyclerItemNormalHolder(view?.context, view)
    }
}