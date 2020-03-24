package com.sea.user.activity.address.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class NAddressListModelReq(
    val command: Int = 12
)

class NAddressListModelResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: List<AddressListItem> = listOf()
)


@Parcelize
data class AddressListItem(
    val accept_name: String = "",
    val address: String = "",
    val area: String = "",
    val city: String = "",
    val id: Int = 0,
    val is_default: Int = 0,
    val phone: String = "",
    val province: String = ""
) : Parcelable