package com.sea.custom.ui.delicacy.comment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.presenter.comment.CommentItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class DelicacyCommentAdapter(mList: List<CommentItem>) :
    BaseQuickAdapter<CommentItem, BaseViewHolder>(
        R.layout.item_delicacy_comment,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: CommentItem) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivHead),
            Constants.baseUrl.plus(item.avatar)
        )
        helper?.setText(R.id.tvName, item.user_name)
        helper?.setText(R.id.tvContent, item.content)
        helper?.setText(R.id.tvTime, item.add_time)
        val recyclerView = helper?.getView<RecyclerView>(R.id.rvApplyComment)
        recyclerView?.layoutManager = LinearLayoutManager(helper?.itemView?.context)
        recyclerView?.adapter = ApplyCommentAdapter(item.son_list)
    }
}