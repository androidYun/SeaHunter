package com.sea.custom.presenter.channel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class NChannelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<NChannelItem> = listOf(),
    val totalCount: Int
)

@Parcelize
data class NChannelItem(
    val address: String? = "",
    val albums: List<Album?>? = listOf(),
    val category_id: Int? = 0,
    val category_name: String? = "",
    val mater: String? = "",
    val channel_id: Int? = 0,
    val click: Int? = 0,
    val collect_num: Int? = 0,
    val comment_num: Int? = 0,
    val content: String? = "",
    val eat_way: String? = "",
    val id: Int? = 0,
    val img_url: String? = "",
    val is_collect: Boolean? = false,
    val mobile: String? = "",
    val place: String? = "",
    val product_code: String? = "",
    val share: Int? = 0,
    val sub_title: String? = "",
    val tags: String? = "",
    val title: String? = "",
    val video_src: String? = "",
    val webchat: String? = "",
    val weight: String? = "",
    val worker_no: String? = "",
    val zan: Int? = 0,
    val zhaiyao: String? = "",
    val is_zan: Boolean? = false,
    val vr_url: String? = "",
    val group_id: Int? = -1
) : Parcelable

class NChannelModelReq(
    private val command: Int = 30,
    var channel_name: String = "",
    var key: String = "",
    var category_id: Int? = -1,
    val page_size: Int = 20,
    var page_index: Int = 1,
    var sort: Int = 0,//排序 (0:默认 1：销量 2:最新 3：最热（查看最多），4,离我最近)
    var is_red: Int = -1//is_red  推荐：1，其他不要求
)

@Parcelize
data class Album(
    val add_time: String? = "",
    val article_id: Int? = 0,
    val channel_id: Int? = 0,
    val id: Int? = 0,
    val original_path: String? = "",
    val remark: String? = "",
    val thumb_path: String? = ""
) : Parcelable
