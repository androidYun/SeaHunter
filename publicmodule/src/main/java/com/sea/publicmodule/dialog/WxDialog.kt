package com.sea.publicmodule.dialog

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager

import com.sea.publicmodule.R
import com.sea.publicmodule.utils.weixin.WeixinShareManager
import kotlinx.android.synthetic.main.dialog_select_share.*

class WxDialog(context: Context, private val shareCallBack: ShareCallBack) :
    Dialog(context, R.style.style_dialog) {

    init {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.dialog_select_share)
        val dm = DisplayMetrics()
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        val lp: WindowManager.LayoutParams? = window?.attributes
        lp?.width = dm.widthPixels
        window?.attributes = lp
        window?.setGravity(Gravity.BOTTOM)
        tvCancel.setOnClickListener { dismiss() }
        tvWx.setOnClickListener {
            dismiss()
            shareCallBack.shareWxSuccess(WeixinShareManager.WEIXIN_SHARE_TYPE_TALK)
        }
        tvCircle.setOnClickListener {
            dismiss()
            shareCallBack.shareWxSuccess(WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS)
        }

    }
}

interface ShareCallBack {

    fun shareWxSuccess(shareType: Int)
}
