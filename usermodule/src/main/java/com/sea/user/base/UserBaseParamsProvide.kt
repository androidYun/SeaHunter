package com.sea.user.base

import com.sea.user.common.Constants
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.init.BaseParamsProvide

class UserBaseParamsProvide : BaseParamsProvide {
    override fun getBaseUrl(): String {
        return Constants.netBaseUrl
    }

    override fun getToken(): String {
        return ""
    }

    override fun getUserId(): Int {
        return UserInformSpUtils.getUserId()
    }
}