package com.sea.custom.presenter.collection

class NDelicacyCollectionResponse(
    val code: Int = -1,
    val msg: String = ""
)

class NDelicacyCollectionModelReq(
    private val command: Int = 34,
    private val channel_name: String = "",
    private val article_id: Int = -1
)

class NCancelDelicacyCollectionModelReq(
    private val command: Int = 35,
    private val channel_name: String = "",
    private val article_id: Int = -1
)