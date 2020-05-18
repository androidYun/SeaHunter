package com.sea.publicmodule.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.sea.publicmodule.R
import com.xhs.baselibrary.base.BaseActivity
import java.util.*

class DataPickerActivity : BaseActivity() {
    //用来存储年月日
    var year = 0
    var month = 0
    var day = 0
    //存储页面上的日期选择器
    var datePicker: DatePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_picker)
        //获取页面上的日期选择器
        datePicker = findViewById<View>(R.id.datePicker) as DatePicker
        //获取当前日期
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH]
        day = calendar[Calendar.DAY_OF_MONTH]
        //初始化日期选择器并设置日期改变监听器
        datePicker!!.init(year, month, day) { view, year, monthOfYear, dayOfMonth ->
            //获取选中的年月日
            this@DataPickerActivity.year = year
            //月份是从0开始的
            month = monthOfYear + 1
            day = dayOfMonth
            setResult(
                date_result_code,
                Intent().apply { putExtra(date_data_key, "$year-$month-$dayOfMonth") })
            finish()
        }
    }

    companion object {
        const val date_result_code = 100
        const val date_data_key = "date_data_key"

    }
}