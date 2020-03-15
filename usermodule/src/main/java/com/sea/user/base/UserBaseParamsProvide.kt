package com.sea.user.base

import com.xhs.baselibrary.init.BaseParamsProvide

class UserBaseParamsProvide : BaseParamsProvide {
    override fun getBaseUrl(): String {
        return "http://hunter.hnzhiling.com/handle/"
    }

    override fun getToken(): String {
        return ""
    }
}