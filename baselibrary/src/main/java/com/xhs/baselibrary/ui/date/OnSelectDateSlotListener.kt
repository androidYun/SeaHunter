package com.xhs.baselibrary.ui.date

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/01/2020.
 * description:
 */
interface OnSelectDateSlotListener {
    fun selectSuccess(startTime: String, endTime: String)
    fun selectStartTimeSuccess(startTime: String)
}