package com.sea.user.activity.feedback.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface FeedBackListContact {

    interface IFeedBackListView : IBaseView {

        fun loadFeedBackListSuccess(mList: List<FeedBackListItem>, totalCount: Int)

        fun loadFeedBackListFail(throwable: Throwable)

    }

    interface IFeedBackListPresenter {
        fun loadFeedBackList(nFeedBackListModelReq: NFeedBackListModelReq)
    }
}