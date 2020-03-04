package com.xhs.baselibrary.utils.sp

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 18/09/2019.
 * description:
 */
object IpAddressSpUtils {
    private const val IP_AND_PORT_KEY = "IP_AND_PORT_KEY"

    private const val IP_NUM_KEY = "IP_NUM_KEY"

    private var ipAddress: String = ""

    private var baseSharePreUtils: BaseSharePreUtils

    private const val IP_ADDRESS_SP_KEY = "IP_ADDRESS_SP_KEY"


    init {
        baseSharePreUtils = BaseSharePreUtils(IP_ADDRESS_SP_KEY)
    }


    fun setIpAddress(ipAddress: String) {
        this.ipAddress = ipAddress
        baseSharePreUtils.putString(IP_AND_PORT_KEY, ipAddress)
    }

    fun getIpAddress(defaultIpAddress: String = ""): String {
        if (ipAddress.isNullOrEmpty()) {
            return baseSharePreUtils.getString(IP_AND_PORT_KEY, defaultIpAddress)
        }
        return ipAddress
    }

    fun setIpNum(ipNum: String) {
        baseSharePreUtils.putString(IP_NUM_KEY, ipNum)
    }

    fun getIpNum(defaultIpNum: String = ""): String {
        return baseSharePreUtils.getString(IP_NUM_KEY, defaultIpNum)
    }
}