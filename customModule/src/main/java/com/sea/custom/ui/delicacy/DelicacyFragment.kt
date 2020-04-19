package com.sea.custom.ui.delicacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.ui.delicacy.adapter.DelicacyKindAdapter
import com.sea.custom.ui.delicacy.adapter.DelicacyTypeAdapter
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_delicacy_layout.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyFragment : BaseFragment(), DelicacyContact.IDelicacyView {

    private val mDelicacyPresenter by lazy { DelicacyPresenter().apply { attachView(this@DelicacyFragment) } }

    private lateinit var mDelicacyKindAdapter: DelicacyKindAdapter


    private lateinit var mDelicacyTypeAdapter: DelicacyTypeAdapter

    private val delicacyKindList = mutableListOf<NDelicacyKindItem>()
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
        delicacyKindList.add(NDelicacyKindItem("鱼类", R.mipmap.food_nav1))
        delicacyKindList.add(NDelicacyKindItem("虾类", R.mipmap.food_nav2))
        delicacyKindList.add(NDelicacyKindItem("螃蟹", R.mipmap.food_nav3))
        delicacyKindList.add(NDelicacyKindItem("章鱼", R.mipmap.food_nav4))
        delicacyKindList.add(NDelicacyKindItem("海参", R.mipmap.food_nav5))
        delicacyKindList.add(NDelicacyKindItem("花甲", R.mipmap.food_nav6))
        delicacyKindList.add(NDelicacyKindItem("生蚝", R.mipmap.food_nav7))
        delicacyKindList.add(NDelicacyKindItem("全部", R.mipmap.food_nav8))
        /*种类*/
        rvDelicacyKind.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mDelicacyKindAdapter = DelicacyKindAdapter(delicacyKindList)
        rvDelicacyKind.adapter = mDelicacyKindAdapter

        /*类型*/
        delicacyTypeList.add(NDelicacyTypeItem("店内美食", "全部没事", R.mipmap.nav_food))
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
        mDelicacyPresenter.loadDelicacy(NDelicacyModelReq())
    }

    private fun initListener() {

    }

    override fun loadDelicacySuccess(content: Any) {


    }

    override fun loadDelicacyFail(throwable: Throwable) {
        handleError(throwable)
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