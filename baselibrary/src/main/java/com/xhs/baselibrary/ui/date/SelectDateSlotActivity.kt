package com.xhs.baselibrary.ui.date

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.xhs.baselibrary.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_select_date_slot.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/01/2020.
 * description:
 */
class SelectDateSlotActivity : BaseActivity() {

    private val currentYear by lazy {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        calendar.get(Calendar.YEAR)
    }
    private val currentMonth by lazy {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        calendar.get(Calendar.MONTH)
    }
    private val currentDay by lazy {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        calendar.get(Calendar.DAY_OF_MONTH)
    }

    private var endYear: Int = 0

    private lateinit var selectDateSlotAdapter: SelectDateSlotAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date_slot)
        initData()
        initListener()

    }

    private fun initListener() {
        selectDateSlotAdapter.setOnLoadMoreListener({
            for (i in endYear..endYear + 2) {
                yearList.addAll(createYearList(i))
            }
            endYear += 2
            selectDateSlotAdapter.loadMoreComplete()
        }, rvDate)
        selectDateSlotAdapter.onSelectDateListIterator = object : OnSelectDateSlotListener {
            override fun selectSuccess(startTime: String, endTime: String) {
                tvStartTime.text = startTime
                tvEndTime.text = endTime
            }

            override fun selectStartTimeSuccess(startTime: String) {
                tvStartTime.text = startTime
                tvEndTime.text = ""
            }
        }
        tvFinish.setOnClickListener {
            val startTime = tvStartTime.text.toString()
            val endTime = tvEndTime.text.toString()
            if (!startTime.isNullOrBlank() && !endTime.isNullOrBlank()) {
                setResult(RESULT_CODE_KEY, Intent().putExtras(Bundle().apply {
                    putString(START_TIME_KEY, startTime)
                    putString(END_TIME_KEY, endTime)
                }))
                finish()
            }
        }
        tvClear.setOnClickListener {
            setResult(RESULT_CODE_KEY, Intent().putExtras(Bundle().apply {
                putString(START_TIME_KEY, "")
                putString(END_TIME_KEY, "")
            }))
            finish()
        }
    }

    private val yearList = mutableListOf<NDateModel>()
    private fun initData() {
        endYear = currentYear + 2
        for (i in currentYear - 2..endYear) {
            yearList.addAll(createYearList(i))
        }
        selectDateSlotAdapter = SelectDateSlotAdapter(yearList)
        rvDate.adapter = selectDateSlotAdapter
        val gridLayoutManager = GridLayoutManager(this, 7)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 43 == 0) {
                    7
                } else {
                    1
                }
            }
        }
        rvDate.layoutManager = gridLayoutManager
        val currentPosition = getCurrentPosition(getTimeTime(currentYear, currentMonth, 0))
        rvDate.scrollToPosition(currentPosition)
    }

    private fun getCurrentPosition(date: String): Int {
        yearList.mapIndexed { index, nDateModel ->
            if (nDateModel.date == date) {
                return index
            }
        }
        return 0
    }

    /**
     * 产生今年的列表
     */
    private fun createYearList(year: Int): List<NDateModel> {
        val monthList = mutableListOf<NDateModel>()
        for (i in 1..12) {
            val dayOfMonth = getDayOfMonth(year, i)
            val weekOfDay = getWeekOfDay(year, i - 1)
            val createDateCapacity = createDateCapacity()
            var month = i - 1
            monthList.add(NDateModel(getYearMonthDay(year, month + 1), "", 0, 2))
            for (j in 1..createDateCapacity) {
                var day = j - weekOfDay + 1//因为 前面是空白的需要减掉而且星期数字比空白多1
                when {
                    j < weekOfDay -> monthList.add(NDateModel("", "", 0, 1))
                    j <= dayOfMonth + (weekOfDay - 1) -> monthList.add(//因为 每月第一天的星期几的数字要大于目前星期 比如 星期二 是3 日期日 是 1
                            NDateModel(
                                    getTimeTime(year, month, day), day.toString(),
                                    getTime(year, month, day),
                                    1
                            )
                    )
                    else -> monthList.add(NDateModel("", "", getTime(2020, month, day), 1))
                }
            }
        }
        return monthList
    }

    /**
     * 获取日期的long类型
     */
    private fun getTime(year: Int, month: Int, day: Int): Long {
        val date = Calendar.getInstance()
        date.set(year, month, day, 0, 0)
        return date.timeInMillis
    }

    /**
     * 获取日期的long类型
     */
    private fun getTimeTime(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, 0, 0)
        val format0 = SimpleDateFormat("yyyy-MM-dd")
        return format0.format(calendar.time)//这个就是把时间戳经过处理得到期望格式的时间
    }

    /**
     * 获取日期的long类型
     */
    private fun getYearMonthDay(year: Int, month: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 0, 0, 0)
        val format0 = SimpleDateFormat("yyyy年MM月")
        return format0.format(calendar.time)//这个就是把时间戳经过处理得到期望格式的时间
    }


    /**
     * 获取某月第一天星期几
     *
     * 1 是星期日 2 是星期1 3是星期二  4是星期三 5是星期四  6是星期5  7是星期六
     */
    private fun getWeekOfDay(year: Int, month: Int): Int {
        val date = Calendar.getInstance()
        date.set(year, month, 1, 0, 0)
        return date.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * dayOfWeek 每月第一天是星期几  每月多少天
     *
     */
    private fun createDateCapacity(): Int {
        return 42
    }

    /**
     * 获取某月多少天
     */
    private fun getDayOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 0)
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    companion object {
        const val START_TIME_KEY = "START_TIME_KEY"
        const val END_TIME_KEY = "END_TIME_KEY"
        const val RESULT_CODE_KEY = 0
    }
}