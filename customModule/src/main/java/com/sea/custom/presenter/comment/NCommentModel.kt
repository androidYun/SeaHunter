package com.sea.custom.presenter.comment

class NCommentResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<CommentItem> = listOf()
)

data class CommentItem(
    val add_time: String = "",
    val avatar: String = "",
    val content: String = "",
    val id: Int = 0,
    val user_id: Int = 0,
    val user_name: String = "",
    val son_list: List<CommentItem> = listOf()
)

data class NCommentModelReq(
    private val command: Int = 33,
    private val channel_name: String = "",
    private val article_id: Int = -1,
    private val page_size: Int = 10000,
    private val page_index: Int = 0

)


data class NSubmitCommentModelReq(
     val command: Int = 32,
     var channel_name: String = "",
     var content: String = "",
     var article_id: Int = -1,
     var parent_id: Int = -1

)

