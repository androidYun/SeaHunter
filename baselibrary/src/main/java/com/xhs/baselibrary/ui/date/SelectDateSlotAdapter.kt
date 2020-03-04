package com.xhs.baselibrary.ui.date

import android.graphics.Color
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xhs.baselibrary.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/01/2020.
 * description:
 */
class SelectDateSlotAdapter(private val mList: List<NDateModel>) :
    BaseMultiItemQuickAdapter<NDateModel, BaseViewHolder>(mList) {
    private var oddNumber = 0//奇数
    private var evenNumber = 0//偶数
    private var clickNumber = 0
    private val whiteColor = Color.parseColor("#FFFFFF")
    var onSelectDateListIterator: OnSelectDateSlotListener? = null
    private val currentDate: String by lazy {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        val format0 = SimpleDateFormat("yyyy-MM-dd")
        format0.format(calendar.time)//这个就是把时间戳经过处理得到期望格式的时间
    }

    init {
        addItemType(1, R.layout.item_select_date_slot)
        addItemType(2, R.layout.item_select_date_slot_title)
    }

    override fun convert(helper: BaseViewHolder, item: NDateModel) {
        when (helper.itemViewType) {
            1 -> {
                helper.setGone(R.id.view, currentDate == item.date)
                helper.setText(R.id.tvDate, item.day)
                helper.getView<TextView>(R.id.tvDate).setBackgroundColor(whiteColor)
                helper.getView<TextView>(R.id.tvDate).setTextColor(Color.parseColor("#333333"))
                if (oddNumber != 0 && oddNumber == helper.layoutPosition) {
                    helper.getView<TextView>(R.id.tvDate).setBackgroundResource(R.drawable.shape_select_date_slot)
                    helper.getView<TextView>(R.id.tvDate).setTextColor(whiteColor)
                } else if (evenNumber != 0 && evenNumber == helper.layoutPosition) {
                    helper.getView<TextView>(R.id.tvDate).setBackgroundResource(R.drawable.shape_select_date_slot)
                    helper.getView<TextView>(R.id.tvDate).setTextColor(whiteColor)
                }
                if (oddNumber != 0 && evenNumber != 0 && !item.date.isNullOrBlank()) {
                    if ((helper.layoutPosition in  oddNumber+1 until evenNumber) || helper.layoutPosition in evenNumber+1 until oddNumber) {
                        helper.getView<RelativeLayout>(R.id.rlSelectDate)
                            .setBackgroundColor(Color.parseColor("#EDF2FA"))
                        helper.getView<TextView>(R.id.tvDate).setBackgroundColor(Color.parseColor("#EDF2FA"))
                    } else {
                        helper.getView<RelativeLayout>(R.id.rlSelectDate)
                            .setBackgroundColor(whiteColor)
                    }
                } else {
                    helper.getView<RelativeLayout>(R.id.rlSelectDate).setBackgroundColor(whiteColor)
                }
                helper.getView<TextView>(R.id.tvDate).setOnClickListener {
                    if (clickNumber % 2 == 0) {//点击上次是偶数时  清空第一次点击
                        oddNumber = helper.layoutPosition
                        onSelectDateListIterator?.selectStartTimeSuccess(mList[oddNumber].date)
                        evenNumber = 0
                    } else {
                        evenNumber = helper.layoutPosition
                        if (oddNumber != 0 && evenNumber != 0) {
                            if (oddNumber <= evenNumber) {//那个小那个是开始日期
                                onSelectDateListIterator?.selectSuccess(mList[oddNumber].date, mList[evenNumber].date)
                            } else {
                                onSelectDateListIterator?.selectSuccess(mList[evenNumber].date, mList[oddNumber].date)
                            }

                        }
                    }
                    clickNumber++
                    notifyDataSetChanged()
                }
            }
            2 -> {
                helper.setText(R.id.tvDateTitle, item.date)
            }
        }
    }
}