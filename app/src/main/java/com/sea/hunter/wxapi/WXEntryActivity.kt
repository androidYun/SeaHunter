package com.sea.hunter.wxapi

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter

import com.sea.hunter.R
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils

class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler,
    PraiseShareContact.IPraiseShareView {
    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@WXEntryActivity) } }
    private val nPraiseShareModelReq = NPraiseShareModelReq()

    private var wxapi: IWXAPI? = null

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val channelName = intent.extras?.getString("channelName")
        println("打印参数" + channelName)
        wxapi!!.handleIntent(intent, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxentry)

        wxapi = WXAPIFactory.createWXAPI(this, WeixiShareUtil.getWeixinAppId())
        wxapi!!.handleIntent(intent, this)
    }

    /**
     * 微信发送请求到第三方应用时，会回调到该方法
     */
    override fun onReq(baseReq: BaseReq) {
        // 这里不作深究
    }

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     * app发送消息给微信，处理返回消息的回调
     */
    override fun onResp(baseResp: BaseResp) {
        when (baseResp.errCode) {
            // 正确返回
            BaseResp.ErrCode.ERR_OK -> when (baseResp.type) {
                // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                    ToastUtils.show("分享成功")
                     mPraiseSharePresenter.loadPraiseShare(nPraiseShareModelReq)
                }
                else -> {
                }
            }
            else ->
                // 错误返回
                when (baseResp.type) {
                    // 微信分享
                    ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                        Log.i("WXEntryActivity", ">>>errCode = " + baseResp.errCode)
                        finish()
                    }
                    else -> {
                    }
                }
        }
    }

    override fun loadPraiseShareSuccess(content: Any) {
        finish()
    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        finish()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
