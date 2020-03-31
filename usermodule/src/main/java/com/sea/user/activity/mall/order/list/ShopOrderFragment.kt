package com.sea.user.activity.mall.order.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.user.R
import com.sea.user.utils.DeviceUtils
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_shop_order_list.*
import kotlinx.android.synthetic.main.include_tab_layout_view_pager.*

class ShopOrderFragment : BaseFragment() {

    private val status by lazy { arguments?.getInt(order_status_key) ?: 0 }

    private val tabTitle = mutableListOf(
        ShopOrderModel("全部", 0),
        ShopOrderModel("待付款", 1),
        ShopOrderModel("待发货", 2),
        ShopOrderModel("待收货", 3),
        ShopOrderModel("已完成", 4)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_shop_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (!DeviceUtils.isTabletDevice()) {
            initToolbar(toolbar, "我的订单", false)
        }
    }

    /**
     * Fragment中初始化Toolbar
     * @param toolbar
     * @param title 标题
     * @param isDisplayHomeAsUp 是否显示返回箭头
     */
    private fun initToolbar(toolbar: Toolbar?, title: String?, isDisplayHomeAsUp: Boolean) {
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        val actionBar: ActionBar? = appCompatActivity.supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp)
        }
    }


    private fun initView() {
        viewPage.adapter = ShopOrderPageAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPage)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        if (status <= 4)
            viewPage.currentItem = status
    }

    inner class ShopOrderPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return ShopOrderListFragment.getInstance(tabTitle[position].type)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitle[position].titleName
        }

        override fun getCount(): Int {
            return tabTitle.size
        }
    }

    class ShopOrderModel(val titleName: String, val type: Int)

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (this.view != null) {
            this.view?.visibility = if (menuVisible) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private const val order_status_key = "order_status_key"
        fun getInstance(status: Int = 0) = ShopOrderFragment().apply {
            arguments = Bundle().apply {
                putInt(order_status_key, status)
            }
        }
    }
}