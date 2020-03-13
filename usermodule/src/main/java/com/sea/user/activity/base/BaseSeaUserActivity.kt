package com.sea.user.activity.base

import android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
import android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.sea.user.weight.SideBarView
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ScreenUtils


open class BaseSeaUserActivity : BaseActivity() {


    override fun setContentView(layoutResID: Int) {
        if (isTabletDevice()) {
            super.setContentView(getSideBarContentView(layoutResID))
        } else {
            super.setContentView(layoutResID)
        }
    }

    private var sideBarView: SideBarView? = null
    private fun getSideBarContentView(layoutResID: Int): View {
        sideBarView =
            SideBarView(this)
        sideBarView?.layoutParams = ViewGroup.LayoutParams(
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

    fun selectTab(text: String) {
        sideBarView?.selectTab(text)
    }

    private fun isTabletDevice(): Boolean {
        return resources.configuration.screenLayout and SCREENLAYOUT_SIZE_MASK >= SCREENLAYOUT_SIZE_LARGE
    }
}