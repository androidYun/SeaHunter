package com.sea.custom.ui.delicacy.introduce

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import kotlinx.android.synthetic.main.activity_delicacy_introduce.*

class DelicacyIntroduceActivity : BaseActivity(), DelicacyIntroduceContact.IDelicacyIntroduceView {

    private val mDelicacyIntroducePresenter by lazy {
        DelicacyIntroducePresenter().apply {
            attachView(
                this@DelicacyIntroduceActivity
            )
        }
    }

    private val nDelicacyIntroduceReq = NDelicacyIntroduceModelReq()

    private val mDelicacyIntroduceList = mutableListOf<DelicacyIntroduceItem>()

    private lateinit var mDelicacyIntroduceAdapter: DelicacyIntroduceAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delicacy_introduce)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mDelicacyIntroduceAdapter = DelicacyIntroduceAdapter(mDelicacyIntroduceList)
        rvDelicacyIntroduce.layoutManager = LinearLayoutManager(this)
        rvDelicacyIntroduce.adapter = mDelicacyIntroduceAdapter
    }

    private fun initData() {
        mDelicacyIntroducePresenter.loadDelicacyIntroduce(nDelicacyIntroduceReq)
    }

    private fun initListener() {
        swipeDelicacyIntroduce.setOnRefreshListener {
            mDelicacyIntroducePresenter.loadDelicacyIntroduce(nDelicacyIntroduceReq)
        }
        mDelicacyIntroduceAdapter.setOnLoadMoreListener({
            if (nDelicacyIntroduceReq.page_index * nDelicacyIntroduceReq.page_size < totalCount) {
                mDelicacyIntroducePresenter.loadDelicacyIntroduce(nDelicacyIntroduceReq)
            } else {
                mDelicacyIntroduceAdapter.loadMoreEnd()
            }
        }, rvDelicacyIntroduce)
    }

    override fun loadDelicacyIntroduceSuccess(mList: List<DelicacyIntroduceItem>, totalCount: Int) {
        if (nDelicacyIntroduceReq.page_index == 1) {
            mDelicacyIntroduceList.clear()
        }
        this.totalCount = totalCount
        mDelicacyIntroduceList.addAll(mList)
        mDelicacyIntroduceAdapter.notifyDataSetChanged()
        mDelicacyIntroduceAdapter.loadMoreComplete()
        swipeDelicacyIntroduce.isRefreshing = false
        nDelicacyIntroduceReq.page_index++

    }

    override fun loadDelicacyIntroduceFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyIntroduce.isRefreshing
        mDelicacyIntroduceAdapter.loadMoreComplete()
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