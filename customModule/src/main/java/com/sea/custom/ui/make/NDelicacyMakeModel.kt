package com.sea.custom.ui.make

class NDelicacyMakeResponse(
    val code: Int = -1,
    val msg: String = "",
    val totalCount: Int = 0,
    val data: List<NMineDelicacyMakeItem> = listOf()
) {
    class Data
}

class NDelicacyMakeModelReq

class NMineDelicacyMakeItem()