package com.sea.user.base

import com.sea.user.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.init.BaseParamsProvide

class UserBaseParamsProvide : BaseParamsProvide {
    override fun getBaseUrl(): String {
        return "http://hunter.hnzhiling.com/handle/"
    }

    override fun getToken(): String {
        return ""
    }

    override fun getUserId(): Int {
        return UserInformSpUtils.getUserId()
    }
}