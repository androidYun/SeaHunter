package com.sea.custom.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_club_layout.*
import com.xhs.baselibrary.base.BaseFragment

class ClubFragment : BaseFragment(), ClubContact.IClubView {

    private val mClubPresenter by lazy { ClubPresenter().apply { attachView(this@ClubFragment) } }

    private lateinit var mRecommendActivityAdapter: RecommendActivityAdapter

    private val mRecommendList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_club_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        mRecommendActivityAdapter = RecommendActivityAdapter(mRecommendList)
        if (DeviceUtils.isTabletDevice()) {
            rvRecommendAction.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvRecommendAction.layoutManager = LinearLayoutManager(context)
        }
        rvRecommendAction.adapter = mRecommendActivityAdapter
    }

    private fun initData() {
        mClubPresenter.loadClub(NClubModelReq())
    }

    private fun initListener() {

    }

    override fun loadClubSuccess(content: Any) {


    }

    override fun loadClubFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = ClubFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}