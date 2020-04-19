package com.sea.custom.ui.delicacy

class NDelicacyResponse(
    val code: Int = -1,
    val msg: String = "",
    val data: Data = Data()
) {
    class Data
}

class NDelicacyModelReq


class NDelicacyKindItem(
    val delicacyKindTitle: String = "",
    val resId: Int
)

class NDelicacyTypeItem(
    val delicacyTypeTitle: String = "",
    val delicacyTypeContent: String = "",
    val resId: Int
)