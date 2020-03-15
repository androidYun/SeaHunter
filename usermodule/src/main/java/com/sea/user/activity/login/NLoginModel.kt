package com.sea.user.activity.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class NLoginResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: UserInformModel = UserInformModel()
)

@Parcelize
class UserInformModel(
    val amount: Double = 0.0,
    val avatar: String = "",
    val exp: Int = 0,
    val id: Int = 0,
    val nick_name: String = "",
    val phone: String = "",
    val point: Int = 0,
    val status: Int = 0,
    val user_name: String = ""
) : Parcelable

class NLoginModelReq(var phone: String, var password: String, val command: Int = 3)