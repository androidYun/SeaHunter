package com.sea.custom.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.WindowManager
import com.sea.custom.R
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.xhs.baselibrary.utils.ToastUtils
import com.xhs.publicmodule.activity.DataPickerActivity
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.*

class ApplyShipDialog(
    private val context: Activity,
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
        ivIcon.bringToFront()
    }


    private fun initListener() {
        val name = tvName.text.toString()
        val phone = tvPhone.text.toString()
        val wechat = tvWechat.text.toString()
        val birthday = tvBirthday.text.toString()
        tvBirthday.setOnClickListener {
            context.startActivityForResult(
                Intent(
                    context,
                    DataPickerActivity::class.java
                ), 0
            )
        }
        tvSubmit.setOnClickListener {
            if (name.isNullOrBlank()) {
                ToastUtils.show("姓名不能为空")
                return@setOnClickListener
            }
            if (phone.isNullOrBlank()) {
                ToastUtils.show("手机号不能为空")
                return@setOnClickListener
            }
            if (wechat.isNullOrBlank()) {
                ToastUtils.show("微信不能为空")
                return@setOnClickListener
            }
            if (birthday.isNullOrBlank()) {
                ToastUtils.show("生日不能为空")
                return@setOnClickListener
            }
            nApplyMemberModel.name = name
            nApplyMemberModel.phone = phone
            nApplyMemberModel.webchat = wechat
            nApplyMemberModel.birthday = birthday
            applyMemberShipListener.applyMemberShipSuccess(nApplyMemberModel)
        }

    }

    fun setBirthDay(birthday: String) {
        tvBirthday.text = birthday
    }
}