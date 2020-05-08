package com.sea.custom.ui.club

import android.app.Activity
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.sea.custom.R
import com.sea.custom.presenter.channel.detail.ChannelDetail
import com.sea.custom.presenter.channel.detail.ChannelDetailContact
import com.sea.custom.presenter.channel.detail.ChannelDetailPresenter
import com.sea.custom.presenter.channel.detail.NChannelDetailModelReq
import com.xhs.baselibrary.BaseApplication
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_custom_web.*

class DetailWebFragment : BaseFragment(), ChannelDetailContact.IChannelDetailView {
    val varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var \$img = document.getElementsByTagName('img');for(var p in  \$img){\$img[p].style.width = '100%'; \$img[p].style.height ='auto'}}</script>";


    private val mChannelDetailPresenter by lazy { ChannelDetailPresenter().apply { attachView(this@DetailWebFragment) } }

    private val webId by lazy { arguments?.getInt(web_detail_id_key) ?: -1 }

    private val channelName by lazy { arguments?.getString(channel_name_key) ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_web, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        initWebView()
        initWebViewClient()
    }

    private fun initData() {
        mChannelDetailPresenter.loadChannelDetail(
            NChannelDetailModelReq(
                channel_name = channelName,
                id = webId
            )
        )
    }


    private fun initWebView() {
        val setting = webView.settings
        //自定义UA
        var userAgent = setting.userAgentString
        userAgent += "WebViewDemo"
        setting.userAgentString = userAgent

        /**
         * Webview在安卓5.0之前默认允许其加载混合网络协议内容
         * 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        //自动播放音频autoplay
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setting.mediaPlaybackRequiresUserGesture = false
        }

        setting.javaScriptEnabled = true//设置WebView是否允许执行JavaScript脚本,默认false
        setting.setSupportZoom(true)//WebView是否支持使用屏幕上的缩放控件和手势进行缩放,默认值true
        setting.builtInZoomControls = true//是否使用内置的缩放机制
        setting.displayZoomControls = false//使用内置的缩放机制时是否展示缩放控件,默认值true

        setting.useWideViewPort = true//是否支持HTML的“viewport”标签或者使用wide viewport
        setting.loadWithOverviewMode = true//是否允许WebView度超出以概览的方式载入页面,默认false
        setting.layoutAlgorithm =
            WebSettings.LayoutAlgorithm.SINGLE_COLUMN//设置布局,会引起WebView的重新布局(relayout),默认值NARROW_COLUMNS

        setting.setRenderPriority(WebSettings.RenderPriority.HIGH)//线程优先级(在API18以上已废弃。不建议调整线程优先级，未来版本不会支持这样做)
        setting.setEnableSmoothTransition(true)//已废弃,将来会成为空操作（no-op）,设置当panning或者缩放或者持有当前WebView的window没有焦点时是否允许其光滑过渡,若为true,WebView会选择一个性能最大化的解决方案。例如过渡时WebView的内容可能不更新。若为false,WebView会保持精度（fidelity）,默认值false。
        setting.cacheMode = WebSettings.LOAD_NO_CACHE//重写使用缓存的方式，默认值LOAD_DEFAULT
        setting.pluginState = WebSettings.PluginState.ON//在API18以上已废弃。未来将不支持插件,不要使用
        setting.javaScriptCanOpenWindowsAutomatically = true//让JavaScript自动打开窗口,默认false

        //webview 中localStorage无效的解决方法
        setting.domStorageEnabled = true//DOM存储API是否可用,默认false
        setting.setAppCacheMaxSize((1024 * 1024 * 8).toLong())//设置应用缓存内容的最大值
        val appCachePath = BaseApplication.getsInstance().cacheDir.absolutePath
        setting.setAppCachePath(appCachePath)//设置应用缓存文件的路径
        setting.allowFileAccess = true//是否允许访问文件,默认允许
        setting.setAppCacheEnabled(true)//应用缓存API是否可用,默认值false,结合setAppCachePath(String)使用


        setting.domStorageEnabled = true
    }

    private fun initWebViewClient() {
        val webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                webView.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView.loadUrl(request.url.toString())
                }
                return super.shouldOverrideUrlLoading(view, request)
            }


            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()// 接受所有网站的证书
            }
        }
        webView.webViewClient = webViewClient


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

    override fun loadChannelDetailSuccess(channelDetail: ChannelDetail) {
        webView.loadData(varjs + channelDetail.content, "text/html", "UTF-8");
    }

    override fun loadChannelDetailFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {

        private const val channel_name_key = "channel_name_key"
        private const val web_detail_id_key = "web_detail_id_key"

        fun getInstance(id: Int, channelName: String) = DetailWebFragment().apply {
            arguments = Bundle().apply {
                putString(channel_name_key, channelName)
                putInt(web_detail_id_key, id)
            }
        }
    }
}