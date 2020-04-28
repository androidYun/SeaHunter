package com.sea.custom.ui.club.match

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_club_match.*

class ClubMatchActivity : BaseActivity(), ClubMatchContact.IClubMatchView {

    private val mClubMatchPresenter by lazy { ClubMatchPresenter().apply { attachView(this@ClubMatchActivity) } }

    private val nClubMatchReq = NClubMatchModelReq()

    private val mClubMatchList = mutableListOf<ClubMatchItem>()

    private lateinit var mClubMatchAdapter: ClubMatchAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_match)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mClubMatchAdapter = ClubMatchAdapter(mClubMatchList)
        if (DeviceUtils.isTabletDevice()) {
            rvClubMatch.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvClubMatch.layoutManager = LinearLayoutManager(this)
        }
        rvClubMatch.adapter = mClubMatchAdapter
    }

    private fun initData() {
        mClubMatchPresenter.loadClubMatch(nClubMatchReq)
    }

    private fun initListener() {
        swipeClubMatch.setOnRefreshListener {
            mClubMatchPresenter.loadClubMatch(nClubMatchReq)
        }
        mClubMatchAdapter.setOnLoadMoreListener({
            if (nClubMatchReq.page_index * nClubMatchReq.page_size < totalCount) {
                mClubMatchPresenter.loadClubMatch(nClubMatchReq)
            } else {
                mClubMatchAdapter.loadMoreEnd()
            }
        }, rvClubMatch)
        rgpView.setOnCheckedChangeListener { group, checkedId ->

        }
    }

    override fun loadClubMatchSuccess(mList: List<ClubMatchItem>, totalCount: Int) {
        if (nClubMatchReq.page_index == 1) {
            mClubMatchList.clear()
        }
        this.totalCount = totalCount
        mClubMatchList.addAll(mList)
        mClubMatchAdapter.notifyDataSetChanged()
        mClubMatchAdapter.loadMoreComplete()
        swipeClubMatch.isRefreshing = false
        nClubMatchReq.page_index++

    }

    override fun loadClubMatchFail(throwable: Throwable) {
        handleError(throwable)
        swipeClubMatch.isRefreshing = false
        mClubMatchAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}