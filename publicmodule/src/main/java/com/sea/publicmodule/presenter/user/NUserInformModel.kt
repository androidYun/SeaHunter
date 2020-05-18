package com.sea.publicmodule.presenter.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


class NUserInformResponse(
    val code: Int = 0,
    val command: Int = 0,
    val `data`: UserInformModel = UserInformModel(),
    val msg: String = ""
)


data class NUserInformReq(private val command: Int = 5)

@Parcelize
class UserInformModel(
    val amount: Double = 0.0,
    val avatar: String = "",
    val exp: Int = 0,
    val group_id: Int = 0,//1：普通   2：会员  3：vip
    val group_name: String = "",
    val id: Int = -1,
    val nick_name: String = "",
    val phone: String = "",
    val point: Int = 0,
    val status: Int = 0,
    val user_name: String = ""
) : Parcelable
