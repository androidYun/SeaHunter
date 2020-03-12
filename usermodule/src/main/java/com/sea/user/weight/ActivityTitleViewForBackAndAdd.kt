package com.sea.user.weight


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

import com.sea.user.R

class ActivityTitleViewForBackAndAdd @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : RelativeLayout(context, attributeSet, defaultStyle) {

    private val mNameSpace = "http://schemas.android.com/apk/res/chen.chenximobilesafe"

    init {
        initView(context)
    }//通过上面的传参，实现无论系统调用，哪个构造方法，最终调用的是具有样式的构造方法

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.include_sidebar_layout, this)
    }

    companion object {
        private val TAG = ActivityTitleViewForBackAndAdd::class.java.simpleName
    }
}//调用同名构造方法
//调用同名构造方法