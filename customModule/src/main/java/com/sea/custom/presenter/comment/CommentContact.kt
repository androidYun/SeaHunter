package com.sea.custom.presenter.comment

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface CommentContact {

    interface ICommentView : IBaseView {

        fun loadCommentSuccess(CommentItemList: List<CommentItem>)


        fun submitCommentSuccess()

        fun loadCommentFail(throwable: Throwable)

    }

    interface ICommentPresenter {
        fun loadComment(nCommentModelReq: NCommentModelReq)

        fun submitComment(nCommentModelReq: NSubmitCommentModelReq)
    }
}