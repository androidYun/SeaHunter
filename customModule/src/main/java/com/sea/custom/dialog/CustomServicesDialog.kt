package com.sea.custom.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.sea.custom.R
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.dialog_apply_custom_service_layout.*
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.evName
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.evPhone
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.ivIcon
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.tvSubmit

class CustomServicesDialog(
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
        setContentView(R.layout.dialog_apply_custom_service_layout)
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
        tvSubmit.setOnClickListener {
            val name = evName.text.toString()
            val phone = evPhone.text.toString()
            val address = evAddress.text.toString()
            if (name.isNullOrBlank()) {
                ToastUtils.show("姓名不能为空")
                return@setOnClickListener
            }
            if (phone.isNullOrBlank()) {
                ToastUtils.show("手机号不能为空")
                return@setOnClickListener
            }
            if (address.isNullOrBlank()) {
                ToastUtils.show("请输入详细地址")
                return@setOnClickListener
            }
            nApplyMemberModel.name = name
            nApplyMemberModel.phone = phone
            nApplyMemberModel.address = address
            applyMemberShipListener.applyMemberShipSuccess(nApplyMemberModel)
        }

    }

}