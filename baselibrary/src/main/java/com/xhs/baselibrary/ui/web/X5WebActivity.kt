package com.xhs.baselibrary.ui.web

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.xhs.baselibrary.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_x5_web.*


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/07/2019.
 * description:
 */
 class X5WebActivity : BaseActivity() {


    private val url by lazy { intent?.extras?.getString(WEB_URL_KEY) ?: "" }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x5_web)
        initWebView()
        initWebViewClient()
        webView.loadUrl(url)
    }

    private fun initWebView() {
    }

    private fun initWebViewClient() {
        webView.setOnKeyListener(
            object : View.OnKeyListener {
                override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
                    //这里处理返回键事件
                    if (webView.canGoBack()) {
                        webView.goBack()
                        return true
                    }
                    return false
                }
            })
    }

    companion object {

        private const val WEB_URL_KEY = "WEB_URL_KEY"

        fun getInstance(url: String) = Bundle().apply {
            putString(WEB_URL_KEY, url)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack()
                return true
            } else {
                return super.onKeyDown(keyCode, event)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun isBackAndFinish(activity: Activity) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            activity.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (webView != null) {
            webView.onResume()
            //恢复pauseTimers状态
            webView.resumeTimers()
            webView.reload()
        }
    }

    override fun onPause() {
        super.onPause()
        if (webView != null) {
            webView.onPause()
            //恢复pauseTimers状态
            webView.pauseTimers()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (webView != null) {
            webView.visibility = View.GONE
            webView.loadUrl("about:blank")
            webView.stopLoading()
            webView.webChromeClient = null
            webView.webViewClient = null
            webView.destroy()
        }
    }

}