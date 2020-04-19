package com.sea.custom.ui.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.custom.R
import com.sea.custom.ui.make.list.DelicacyMakeListFragment
import kotlinx.android.synthetic.main.fragment_delicacy_make.*
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class DelicacyMakeFragment : BaseFragment(), DelicacyMakeContact.IDelicacyMakeView {

    private val mDelicacyMakePresenter by lazy { DelicacyMakePresenter().apply { attachView(this@DelicacyMakeFragment) } }

    private val mDelicacyMakeList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_make, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        mDelicacyMakeList.add("推荐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        mDelicacyMakeList.add("娱乐")
        viewPager.adapter = EntertainmentPageAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initData() {
        mDelicacyMakePresenter.loadDelicacyMake(NDelicacyMakeModelReq())
    }

    private fun initListener() {

    }

    override fun loadDelicacyMakeSuccess(content: Any) {


    }

    override fun loadDelicacyMakeFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyMakeFragment().apply {
            arguments = Bundle().apply { }
        }
    }

    inner class EntertainmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return DelicacyMakeListFragment.getInstance()
        }

        override fun getCount(): Int {
            return mDelicacyMakeList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mDelicacyMakeList[position]
        }
    }
}