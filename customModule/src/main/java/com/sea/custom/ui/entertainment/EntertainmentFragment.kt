package com.sea.custom.ui.entertainment

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
import com.sea.custom.R
import com.sea.custom.ui.entertainment.list.EntertainmentListFragment
import kotlinx.android.synthetic.main.fragment_entertainment_layout.*
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class EntertainmentFragment : BaseFragment(), EntertainmentContact.IEntertainmentView {

    private val mEntertainmentPresenter by lazy { EntertainmentPresenter().apply { attachView(this@EntertainmentFragment) } }

    private val mEntertainmentList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_entertainment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "娱乐视频", false)
    }

    private fun initView() {
        mEntertainmentList.add("推荐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        mEntertainmentList.add("娱乐")
        viewPager.adapter = EntertainmentPageAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
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
    private fun initData() {
        mEntertainmentPresenter.loadEntertainment(NEntertainmentModelReq())
    }

    private fun initListener() {

    }

    override fun loadEntertainmentSuccess(content: Any) {


    }

    override fun loadEntertainmentFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = EntertainmentFragment().apply {
            arguments = Bundle().apply { }
        }
    }

    inner class EntertainmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return EntertainmentListFragment.getInstance()
        }

        override fun getCount(): Int {
            return mEntertainmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mEntertainmentList[position]
        }
    }
}