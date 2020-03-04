package com.xhs.baselibrary.ui.date

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/01/2020.
 * description:
 */
/**
 * type 1普通日期 2每个月的日期头部
 */
data class NDateModel(
    var date: String = "",
    val day: String = "",
    val time: Long,
    val type: Int
) : MultiItemEntity {
    override fun getItemType(): Int {
        return type
    }
}