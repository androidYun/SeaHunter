package com.sea.user.activity.mall

import androidx.annotation.DrawableRes

class NSeaFoodMallResponse(
    val code: Int = -1,
    val msg: String = "",
    val `data`: List<SeaCategoryItemModel> = listOf()
)
class SeaCategoryItemModel(
    val id: Int = 0,
    val img_url: String = "",
    val layer: Int = 0,
    val title: String = ""
)


class NSeaFoodMallModelReq(val command: Int = 8)

//海鲜种类
class NKindFood(@DrawableRes val resId: Int, val kindName: String)

class NFoodType(@DrawableRes val resId: Int, val typeName: String, val typeDesc: String)