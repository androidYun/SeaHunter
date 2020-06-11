package com.sea.hunter.wxapi

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter

import com.sea.hunter.R
import com.sea.hunter.SeaHunterApplication
import com.sea.publicmodule.activity.model.ShareMessageEvent
import com.sea.publicmodule.common.CommonParamsUtils
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import org.greenrobot.eventbus.EventBus

class WXEntryActivity : BaseActivity(), IWXAPIEventHandler,
    PraiseShareContact.IPraiseShareView {
    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@WXEntryActivity) } }
    private val nPraiseShareModelReq = NPraiseShareModelReq(click_type = 3)

    private var wxapi: IWXAPI? = null

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val channelName = intent.extras?.getString("channelName")
        wxapi!!.handleIntent(intent, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxentry)
        val channelName = intent.extras?.getString("channelName")
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
                    nPraiseShareModelReq.article_id = CommonParamsUtils.articleId
                    nPraiseShareModelReq.channel_name = CommonParamsUtils.channelName
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
        EventBus.getDefault().post(ShareMessageEvent())
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
