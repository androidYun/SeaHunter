package com.sea.user.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.sea.user.R
import com.xhs.baselibrary.utils.UIUtils

class SideBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : LinearLayout(context, attributeSet, defaultStyle) {


    init {
        initView()
        initData()
        initListener()
    }


    private lateinit var tvSidebarOrdering: TextView
    private lateinit var tvSidebarCar: TextView
    private lateinit var tvSidebarCarNumber: TextView
    private lateinit var tvSidebarOrder: TextView
    private lateinit var tvSidebarMine: TextView
    private fun initView() {
        val inflateView = View.inflate(context, R.layout.include_sidebar_layout, this)
        tvSidebarOrdering = inflateView.findViewById(R.id.tvSidebarOrdering)
        tvSidebarCar = inflateView.findViewById(R.id.tvSidebarOrdering)
        tvSidebarCarNumber = inflateView.findViewById(R.id.tvSidebarCarNumber)
        tvSidebarOrder = inflateView.findViewById(R.id.tvSidebarOrder)
        tvSidebarMine = inflateView.findViewById(R.id.tvSidebarMine)
    }

    private fun initData() {

    }

    private fun initListener() {
        tvSidebarOrdering.setOnClickListener { selectTab(tvSidebarOrdering) }
        tvSidebarCar.setOnClickListener { selectTab(tvSidebarCar) }
        tvSidebarOrder.setOnClickListener { selectTab(tvSidebarOrder) }
        tvSidebarMine.setOnClickListener { selectTab(tvSidebarMine) }
    }

    private fun selectTab(view: TextView) {
        tvSidebarOrdering.setTextColor(UIUtils.getInstance().getColor(R.color.color_747474))
        tvSidebarCar.setTextColor(UIUtils.getInstance().getColor(R.color.color_747474))
        tvSidebarOrder.setTextColor(UIUtils.getInstance().getColor(R.color.color_747474))
        tvSidebarMine.setTextColor(UIUtils.getInstance().getColor(R.color.color_747474))
        tvSidebarOrdering.isActivated = false
        tvSidebarCar.isActivated = false
        tvSidebarOrder.isActivated = false
        tvSidebarMine.isActivated = false
        view.setTextColor(UIUtils.getInstance().getColor(R.color.base_color_theme))
        view.isActivated = true
    }

}