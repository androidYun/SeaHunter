package com.sea.user.activity.login

import android.os.Parcelable
import com.sea.publicmodule.presenter.user.UserInformModel
import kotlinx.android.parcel.Parcelize

class NLoginResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: UserInformModel = UserInformModel()
)

class NLoginModelReq(var phone: String, var password: String, val command: Int = 3)