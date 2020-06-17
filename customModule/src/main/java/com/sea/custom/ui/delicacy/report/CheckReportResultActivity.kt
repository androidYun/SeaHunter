package com.sea.custom.ui.delicacy.report

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.ui.delicacy.introduce.DelicacyIntroduceResultActivity
import com.sea.custom.utils.DeviceUtils
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_check_report.*
import kotlinx.android.synthetic.main.include_search_layout.*

class CheckReportResultActivity : BaseActivity(), ChannelContact.IChannelView {

    private val mChannelPresenter by lazy { ChannelPresenter().apply { attachView(this@CheckReportResultActivity) } }

    private val searchContent by lazy { intent.extras?.getString(search_result_key) ?: "" }

    private val nChannelModelReq = NChannelModelReq()

    private val mCheckReportList = mutableListOf<NChannelItem>()

    private lateinit var mCheckReportAdapter: CheckReportAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_report_result)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mCheckReportAdapter = CheckReportAdapter(mCheckReportList)
        mCheckReportAdapter.emptyView =
            emptyView
        if (DeviceUtils.isTabletDevice()) {
            rvCheckReport.layoutManager =
                GridLayoutManager(this, 4)
        } else {
            rvCheckReport.layoutManager =
                GridLayoutManager(this, 2)
        }
        rvCheckReport.adapter = mCheckReportAdapter
    }

    private fun initData() {
        nChannelModelReq.channel_name = ChannelEnum.report.name
        nChannelModelReq.key = searchContent
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeCheckReport.setOnRefreshListener {
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mCheckReportAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mCheckReportAdapter.loadMoreEnd()
            }
        }, rvCheckReport)
        mCheckReportAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {

                }
                1 -> {

                }
                2 -> {
                    startActivity(Intent(this, CheckReportResultActivity::class.java))
                }
                3 -> {

                }
            }
            lvSearchShop.setOnClickListener {
                startActivityForResult(
                    Intent(this, SearchMallActivity::class.java),
                    SearchMallActivity.search_content_request_code
                )
            }
        }
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mCheckReportList.clear()
        }
        this.totalCount = totalCount
        mCheckReportList.addAll(mList)
        mCheckReportAdapter.notifyDataSetChanged()
        mCheckReportAdapter.loadMoreComplete()
        swipeCheckReport.isRefreshing = false
        nChannelModelReq.page_index++

    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeCheckReport.isRefreshing
        mCheckReportAdapter.loadMoreComplete()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchMallActivity.search_content_request_code && resultCode == SearchMallActivity.search_content_result_code) {
            val searchContent = data?.getStringExtra(SearchMallActivity.search_content_key) ?: ""
            startActivity(Intent(this, DelicacyIntroduceResultActivity::class.java).apply {
                putExtras(
                    DelicacyIntroduceResultActivity.getInstance(
                        searchContent,
                        ChannelEnum.report.name
                    )
                )
            })
        }
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val search_result_key = "search_result_key"
        fun getInstance(searchContent: String = "") = Bundle().apply {
            putString(search_result_key, searchContent)

        }
    }
}