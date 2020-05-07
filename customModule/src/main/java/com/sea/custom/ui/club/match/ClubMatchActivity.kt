package com.sea.custom.ui.club.match

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_club_match.*

class ClubMatchActivity : BaseActivity(), ChannelContact.IChannelView {

    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@ClubMatchActivity
            )
        }
    }
    private val nChannelModelReq = NChannelModelReq()

    private val mClubMatchList = mutableListOf<NChannelItem>()

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
        nChannelModelReq.channel_name=ChannelEnum.game.name
        nChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeClubMatch.setOnRefreshListener {
            nChannelPresenter.loadChannel(nChannelModelReq)
        }
        mClubMatchAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                nChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mClubMatchAdapter.loadMoreEnd()
            }
        }, rvClubMatch)
        rgpView.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btnDefault -> {
                    nChannelModelReq.page_index=1
                    nChannelModelReq.sort = 0
                    nChannelPresenter.loadChannel(nChannelModelReq)
                }
                R.id.btnNew -> {
                    nChannelModelReq.page_index=1
                    nChannelModelReq.sort = 1
                    nChannelPresenter.loadChannel(nChannelModelReq)
                }
                R.id.btnMore -> {
                    nChannelModelReq.page_index=1
                    nChannelModelReq.sort = 2
                    nChannelPresenter.loadChannel(nChannelModelReq)
                }
            }
        }
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mClubMatchList.clear()
        }
        this.totalCount = totalCount
        mClubMatchList.addAll(mList)
        mClubMatchAdapter.notifyDataSetChanged()
        mClubMatchAdapter.loadMoreComplete()
        swipeClubMatch.isRefreshing = false
        nChannelModelReq.page_index++
    }

    override fun loadChannelFail(throwable: Throwable) {
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