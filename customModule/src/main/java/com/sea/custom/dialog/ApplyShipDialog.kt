package com.sea.custom.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.WindowManager
import com.sea.custom.R
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.xhs.publicmodule.activity.DataPickerActivity
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.*

class ApplyShipDialog(
    context: Context,
    private val applyMemberShipListener: ApplyMemberShipListener
) :
    Dialog(context, R.style.style_dialog) {
    init {
        initView()
        initListener()
    }

    private val nApplyMemberModel = NApplyMemberModel()


    private fun initView() {
        setContentView(R.layout.dialog_apply_membership_layout)
        val dm = DisplayMetrics()
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        val lp: WindowManager.LayoutParams? = window?.attributes
        lp?.width = dm.widthPixels
        window?.attributes = lp
    }


    private fun initListener() {
        val name = tvName.text.toString()
        val phone = tvPhone.text.toString()
        val wechat = tvWechat.text.toString()
        val birthday = tvBirthday.text.toString()
        tvBirthday.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    DataPickerActivity::class.java
                )
            )
        }
        tvSubmit.setOnClickListener {
            nApplyMemberModel.name = name
            nApplyMemberModel.phone = phone
            nApplyMemberModel.webchat = wechat
            nApplyMemberModel.birthday = birthday
            applyMemberShipListener.applyMemberShipSuccess(nApplyMemberModel)
        }

    }
}