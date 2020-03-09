package com.sea.user.activity.feedback.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R

class FeedBackListAdapter(mList: List<FeedBackListItem>) :
    BaseQuickAdapter<FeedBackListItem, BaseViewHolder>(R.layout.item_fragment_feed_back_list_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: FeedBackListItem?) {
        helper?.setText(R.id.tvFeedbackContent, "")
        helper?.setText(R.id.tvFeedbackTime, "")
        helper?.setText(R.id.tvReplyTime, "")
        helper?.setText(R.id.tvReplayContent, "")
    }
}