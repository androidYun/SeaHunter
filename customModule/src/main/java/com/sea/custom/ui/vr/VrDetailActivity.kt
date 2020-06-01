package com.sea.custom.ui.vr

import android.os.Bundle
import android.view.KeyEvent
import com.sea.custom.R
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.ui.web.WebFragment

class VrDetailActivity : BaseActivity() {
    private val vrUrl by lazy { intent?.extras?.getString(vr_detail_url) ?: "" }
    private lateinit var webFragment: WebFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityt_vr_detail)
        webFragment=WebFragment.getInstance(vrUrl)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, webFragment).commit()
    }


    companion object {
        private const val vr_detail_url = "vr_detail_url"
        fun getInstance(vrUrl: String = "") = Bundle().apply {
            putString(vr_detail_url, vrUrl)
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           if(webFragment!=null){
               webFragment.isBackAndFinish(this)
           }
        }
        return super.onKeyDown(keyCode, event)
    }
}