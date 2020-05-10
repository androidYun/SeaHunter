package com.xhs.publicmodule.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.xhs.baselibrary.base.BaseActivity;
import com.xhs.publicmodule.R;

import java.util.Calendar;

public class DataPickerActivity extends BaseActivity {

    //用来存储年月日
    int year, month, day;
    //存储页面上的日期选择器
    DatePicker datePicker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_picker);
        //获取页面上的日期选择器
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //初始化日期选择器并设置日期改变监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取选中的年月日
                DataPickerActivity.this.year = year;
                //月份是从0开始的
                DataPickerActivity.this.month = (monthOfYear + 1);
                DataPickerActivity.this.day = dayOfMonth;
                //弹窗显示
                Toast.makeText(DataPickerActivity.this, DataPickerActivity.this.year + "年" + DataPickerActivity.this.month + "月" + DataPickerActivity.this.day + "日", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
