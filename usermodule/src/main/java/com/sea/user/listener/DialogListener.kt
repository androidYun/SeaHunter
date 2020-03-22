package com.sea.user.listener

import com.sea.user.activity.mall.detail.ShopSpecItemSon

interface DialogListener {

    interface SelectShopSpecListener {
        fun selectShopSpecSuccess(number: Int, mList: List<ShopSpecItemSon>)
    }
}