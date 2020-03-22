package com.sea.user.utils.sp

import com.sea.user.activity.login.UserInformModel
import com.xhs.baselibrary.utils.sp.BaseSharePreUtils

object UserInformSpUtils {
    private var baseSharePreUtils: BaseSharePreUtils

    private const val sea_hunter_user_sp_key = "sea_hunter_user_sp_key"
    private const val sea_hunter_user_phone_key = "sea_hunter_user_phone_key"
    private const val sea_hunter_user_password_key = "sea_hunter_user_password_key"
    private const val sea_hunter_user_id_key = "sea_hunter_user_id_key"
    private const val sea_hunter_shop_store_name_key = "sea_hunter_shop_store_name_key"
    private const val sea_hunter_shop_store_id_key = "sea_hunter_shop_store_id_key"


    init {
        baseSharePreUtils = BaseSharePreUtils(sea_hunter_user_sp_key)
    }

    private var userInformModel: UserInformModel = UserInformModel()
    private fun getUserInformModel(): UserInformModel {
        if (userInformModel == null || userInformModel.id < 0) {
            userInformModel = baseSharePreUtils.get(sea_hunter_user_sp_key, UserInformModel())
        }
        return userInformModel
    }

    fun setUserInformModel(userInformModel: UserInformModel) {
        this.userInformModel = userInformModel
        baseSharePreUtils.put(sea_hunter_user_sp_key, userInformModel)
    }

    fun setPhoneNumber(phone: String) {
        baseSharePreUtils.put(sea_hunter_user_phone_key, phone)

    }

    fun getPhoneNumber(default: String = ""): String {
        return baseSharePreUtils.get(sea_hunter_user_phone_key, default)
    }

    fun setPassword(password: String) {
        baseSharePreUtils.put(sea_hunter_user_password_key, password)
    }

    fun getPassword(default: String = ""): String {
        return baseSharePreUtils.get(sea_hunter_user_password_key, default)
    }

    fun getUserId(): Int {
        return getUserInformModel().id
    }

    fun getShopStoreName(default: String) {
        baseSharePreUtils.get(sea_hunter_shop_store_name_key, default)
    }


    fun setShopStoreName(shopStoreName: String) {
        baseSharePreUtils.put(sea_hunter_shop_store_name_key, shopStoreName)
    }

    fun getShopStoreId(default: Int) {
        baseSharePreUtils.get(sea_hunter_shop_store_id_key, default)
    }


    fun setShopStoreId(shopStoreId: Int) {
        baseSharePreUtils.put(sea_hunter_shop_store_id_key, shopStoreId)
    }

}