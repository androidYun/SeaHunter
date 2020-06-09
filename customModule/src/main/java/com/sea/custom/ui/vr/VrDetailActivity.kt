package com.sea.custom.ui.vr

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.ActionBar
import com.sea.custom.R
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.ui.web.WebFragment

class VrDetailActivity : BaseActivity() {
    private val vrUrl by lazy { intent?.extras?.getString(vr_detail_url) ?: "" }
    private val titleContent by lazy { intent?.extras?.getString(web_url_title_key) ?: "" }
    private lateinit var webFragment: WebFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityt_vr_detail)
        webFragment = WebFragment.getInstance(vrUrl)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, webFragment).commit()
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            if (!titleContent.isNullOrBlank()) {
                title = titleContent
            } else {
                actionBar.hide()
            }
        }

    }


    companion object {
        private const val vr_detail_url = "vr_detail_url"
        private const val web_url_title_key = "web_url_title_key"
        fun getInstance(vrUrl: String = "", title: String = "") = Bundle().apply {
            putString(vr_detail_url, vrUrl)
            putString(web_url_title_key, title)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webFragment != null) {
                webFragment.isBackAndFinish(this)
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}