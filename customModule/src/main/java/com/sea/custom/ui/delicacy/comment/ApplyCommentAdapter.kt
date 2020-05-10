package com.sea.custom.ui.delicacy.comment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.presenter.comment.CommentItem
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class ApplyCommentAdapter(mList: List<CommentItem>) :
    BaseQuickAdapter<CommentItem, BaseViewHolder>(
        R.layout.item_apply_comment,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: CommentItem) {
        helper?.setText(R.id.tvName, item.user_name)
        helper?.setText(R.id.tvContent, item.content)
    }
}