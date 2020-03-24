package com.sea.user.listener

import com.sea.user.activity.mall.detail.Good

interface DialogListener {

    interface SelectShopSpecListener {
        fun selectShopSpecSuccess(number: Int,goods: Good)
    }
}