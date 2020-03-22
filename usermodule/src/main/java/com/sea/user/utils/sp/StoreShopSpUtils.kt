package com.sea.user.utils.sp

import com.xhs.baselibrary.utils.sp.BaseSharePreUtils

object StoreShopSpUtils {
    private var baseSharePreUtils: BaseSharePreUtils

      private const val sea_hunter_user_shop_sp_key = "sea_hunter_user_shop_sp_key"

    private const val sea_hunter_user_shop_name_key = "sea_hunter_user_shop_name_key"


    private const val sea_hunter_user_shop_id_key = "sea_hunter_user_shop_id_key"

    init {
        baseSharePreUtils = BaseSharePreUtils(sea_hunter_user_shop_sp_key)
    }

     fun getStoreShopId(default: Int=-1): Int {
        return baseSharePreUtils.get(sea_hunter_user_shop_id_key, default)
    }

     fun setStoreShopId(shopId: Int) {
        baseSharePreUtils.put(sea_hunter_user_shop_id_key, shopId)
    }

     fun getStoreShopName(default: String=""): String {
        return baseSharePreUtils.get(sea_hunter_user_shop_name_key, default)
    }

     fun setStoreShopName(shopName: String) {
        baseSharePreUtils.put(sea_hunter_user_shop_name_key, shopName)
    }
}