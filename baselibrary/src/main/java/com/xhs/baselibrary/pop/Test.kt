package com.xhs.baselibrary.pop

import java.util.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 08/01/2020.
 * description:
 */
abstract class Test<T : Test<T>> {

    internal fun tes(): T {
        return this as T
    }
}
