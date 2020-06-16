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
import com.sea.publicmodule.activity.DataPickerActivity
import kotlinx.android.synthetic.main.dialog_apply_membership_layout.*
import android.app.DatePickerDialog
import java.util.*

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

        tvBirthday.setOnClickListener {
            context.startActivityForResult(
                Intent(
                    context,
                    DataPickerActivity::class.java
                ), 0
            )
        }
        tvBirthday.setOnClickListener {
            showDatePickDialog(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                nApplyMemberModel.birthday = "$year-${month + 1}-$day"
                tvBirthday.text = "$year-${month + 1}-$day"
            })
        }
        tvSubmit.setOnClickListener {
            val name = evName.text.toString()
            val phone = evPhone.text.toString()
            val wechat = evWechat.text.toString()
            val birthday = tvBirthday.text.toString()
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
        ivCancel.setOnClickListener { this.dismiss() }
    }

    /**
     * @description 选择日期弹出框
     * @param listener 选择日期确定后执行的接口
     * @param curDate 当前显示的日期
     * @return
     * @author wqy
     * @time 2020-1-6 14:23
     */
    private fun showDatePickDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day)
        datePickerDialog.show()
    }

    fun setBirthDay(birthday: String) {
        tvBirthday.text = birthday
    }
}