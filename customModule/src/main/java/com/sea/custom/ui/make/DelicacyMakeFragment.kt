package com.sea.custom.ui.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.category.CategoryContact
import com.sea.custom.presenter.category.CategoryPresenter
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.presenter.category.NCategoryModelReq
import com.sea.custom.ui.make.list.DelicacyMakeListFragment
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_delicacy_make.*
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class DelicacyMakeFragment : BaseFragment(), DelicacyMakeContact.IDelicacyMakeView,
    CategoryContact.ICategoryView {

    private val mDelicacyMakePresenter by lazy { DelicacyMakePresenter().apply { attachView(this@DelicacyMakeFragment) } }

    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@DelicacyMakeFragment) } }
    private val mDelicacyMakeList = mutableListOf<NCategoryItem>()
    private lateinit var mEntertainmentPagerAdapter: FragmentPagerAdapter

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
        mEntertainmentPagerAdapter = EntertainmentPageAdapter(childFragmentManager)
        viewPager.adapter = mEntertainmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initData() {
        mDelicacyMakePresenter.loadDelicacyMake(NDelicacyMakeModelReq())
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
    }

    private fun initListener() {
        swipeLayout.setOnRefreshListener {
            mDelicacyMakePresenter.loadDelicacyMake(NDelicacyMakeModelReq())
            mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
        }
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        mDelicacyMakeList.clear()
        mDelicacyMakeList.addAll(mCategoryList)
        mEntertainmentPagerAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing=false
    }

    override fun loadCategoryFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
    }

    override fun loadDelicacyMakeSuccess(content: Any) {
        swipeLayout.isRefreshing = false

    }

    override fun loadDelicacyMakeFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
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
            return mDelicacyMakeList[position].title
        }
    }
}