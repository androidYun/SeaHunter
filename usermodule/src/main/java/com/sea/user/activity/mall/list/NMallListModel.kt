package com.sea.user.activity.mall.list

class NMallListModelReq(
    val command: Int = 9,
    var page_size: Int = 20,
    var page_index: Int = 1,
    var type: Int = 1,//(1:正常商品 2：积分换购)
    var categor_id: Int = 1,//分类id
    var plate: Int = 1,//板块 { 0：全部 1：必吃 2：最新 3：限时 4：精品 }
    var key: String = "",
    var sort: Int = 1//排序 (1:默认 2：销量 3：价格)
)

class NMallListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data(
        val totalCount: Int = 0,
        val mList: List<MallListItem> = listOf()
    )
}

data class MallListItem(
    val category_id: Int = 0,
    val click: Int = 0,
    val content: String = "",
    val goods_no: String = "",
    val id: Int = 0,
    val img_url: String = "",
    val is_hot: Int = 0,
    val is_msg: Int = 0,
    val is_red: Int = 0,
    val is_top: Int = 0,
    val market_price: Int = 0,
    val point: Int = 0,
    val sell_price: Int = 0,
    val stock_quantity: Int = 0,
    val title: String = ""
)