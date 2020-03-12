package com.sea.user.activity.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.sea.user.R
import com.sea.user.weight.SideBarView
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ScreenUtils

open class BaseSeaUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(getSideBarContentView(layoutResID))
    }

    private fun getSideBarContentView(layoutResID: Int): View {
        val sideBarView =
            SideBarView(this)
        sideBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ScreenUtils.getScreenHeight()
        )
        val inflateContentView =
            LayoutInflater.from(this).inflate(layoutResID, null, false)
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        linearLayout.addView(sideBarView)
        linearLayout.addView(inflateContentView)
        return linearLayout
    }
}