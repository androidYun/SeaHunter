package com.sea.custom.ui.delicacy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.category.CategoryContact
import com.sea.custom.presenter.category.CategoryPresenter
import com.sea.custom.presenter.category.NCategoryItem
import com.sea.custom.presenter.category.NCategoryModelReq
import com.sea.custom.ui.delicacy.adapter.DelicacyKindAdapter
import com.sea.custom.ui.delicacy.adapter.DelicacyTypeAdapter
import com.sea.custom.ui.delicacy.introduce.DelicacyIntroduceActivity
import com.sea.custom.ui.delicacy.introduce.DelicacyIntroduceFragment
import com.sea.custom.ui.delicacy.report.CheckReportActivity
import com.sea.custom.ui.delicacy.vr.StoreVrActivity
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_delicacy_layout.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyFragment : BaseFragment(), DelicacyContact.IDelicacyView,
    CategoryContact.ICategoryView {

    private val mDelicacyPresenter by lazy { DelicacyPresenter().apply { attachView(this@DelicacyFragment) } }

    private val mCategoryPresenter by lazy { CategoryPresenter().apply { attachView(this@DelicacyFragment) } }

    private lateinit var mDelicacyKindAdapter: DelicacyKindAdapter


    private lateinit var mDelicacyTypeAdapter: DelicacyTypeAdapter

    private val delicacyKindList = mutableListOf<NCategoryItem>()
    private val delicacyTypeList = mutableListOf<NDelicacyTypeItem>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        /*种类*/
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rvDelicacyKind.layoutManager = layoutManager
        mDelicacyKindAdapter = DelicacyKindAdapter(delicacyKindList)
        rvDelicacyKind.adapter = mDelicacyKindAdapter

        /*类型*/
        delicacyTypeList.clear()
        delicacyTypeList.add(NDelicacyTypeItem("全部美食", "店内美食", R.mipmap.nav_food))
        delicacyTypeList.add(NDelicacyTypeItem("门店VR", "立体展示", R.mipmap.nav_vr))
        delicacyTypeList.add(NDelicacyTypeItem("检测报告", "权威检测", R.mipmap.nav_report))
        delicacyTypeList.add(NDelicacyTypeItem("渔获介绍", "介绍详细", R.mipmap.nav_introduce))
        if (DeviceUtils.isTabletDevice()) {
            rvDelicacyType.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            rvDelicacyType.layoutManager =
                GridLayoutManager(context, 2)
        }

        mDelicacyTypeAdapter = DelicacyTypeAdapter(delicacyTypeList)
        rvDelicacyType.adapter = mDelicacyTypeAdapter

    }

    private fun initData() {
        mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
        mDelicacyPresenter.loadDelicacy(NDelicacyModelReq())
    }

    private fun initListener() {
        mDelicacyTypeAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(context, StoreVrActivity::class.java))
                }
                1 -> {
                    startActivity(Intent(context, StoreVrActivity::class.java))
                }
                2 -> {
                    startActivity(Intent(context, CheckReportActivity::class.java))
                }
                3 -> {
                    startActivity(Intent(context, DelicacyIntroduceActivity::class.java))
                }
            }

        }
        swipeLayout.setOnRefreshListener {
            mCategoryPresenter.loadCategory(NCategoryModelReq(channel_name = ChannelEnum.food.name))
            mDelicacyPresenter.loadDelicacy(NDelicacyModelReq())
        }
    }

    override fun loadCategorySuccess(mCategoryList: List<NCategoryItem>) {
        delicacyKindList.clear()
        delicacyKindList.addAll(mCategoryList)
        mDelicacyKindAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing=false
    }

    override fun loadCategoryFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing=false
    }

    override fun loadDelicacySuccess(content: Any) {

        swipeLayout.isRefreshing=false
    }

    override fun loadDelicacyFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing=false
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}