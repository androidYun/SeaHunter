package com.sea.user.activity.mall

import androidx.annotation.DrawableRes

class NSeaFoodMallResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NSeaFoodMallModelReq

//海鲜种类
class NKindFood(@DrawableRes val resId: Int, val kindName: String)

class NFoodType(@DrawableRes val resId: Int, val typeName: String, val typeDesc: String)
class NFoodRecommend
class NFoodFine