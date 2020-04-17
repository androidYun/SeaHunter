package com.sea.custom.ui.collection.introduce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_activity_delicacy_introduce.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyIntroduceFragment : BaseFragment(), DelicacyIntroduceContact.IDelicacyIntroduceView {

    private val mDelicacyIntroducePresenter by lazy {
        DelicacyIntroducePresenter().apply {
            attachView(
                this@DelicacyIntroduceFragment
            )
        }
    }

    private val nDelicacyIntroduceReq = NDelicacyIntroduceModelReq()

    private val mDelicacyIntroduceList = mutableListOf<DelicacyIntroduceItem>()

    private lateinit var mDelicacyIntroduceAdapter: DelicacyIntroduceAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_activity_delicacy_introduce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mDelicacyIntroduceAdapter = DelicacyIntroduceAdapter(mDelicacyIntroduceList)
        rvDelicacyIntroduce.layoutManager = LinearLayoutManager(context)
        rvDelicacyIntroduce.adapter = mDelicacyIntroduceAdapter
    }

    private fun initData() {
        mDelicacyIntroducePresenter.loadDelicacyIntroduce(nDelicacyIntroduceReq)
    }

    private fun initListener() {
        swipeDelicacyIntroduce.setOnRefreshListener {
            nDelicacyIntroduceReq.pageIndex = 1
            mDelicacyIntroducePresenter.loadDelicacyIntroduce(nDelicacyIntroduceReq)
        }
        mDelicacyIntroduceAdapter.setOnLoadMoreListener({
            if (nDelicacyIntroduceReq.pageIndex * nDelicacyIntroduceReq.pageSize < totalCount) {
                mDelicacyIntroducePresenter.loadDelicacyIntroduce(nDelicacyIntroduceReq)
            } else {
                mDelicacyIntroduceAdapter.loadMoreEnd()
            }
        }, rvDelicacyIntroduce)
    }

    override fun loadDelicacyIntroduceSuccess(mList: List<DelicacyIntroduceItem>, totalCount: Int) {
        if (nDelicacyIntroduceReq.pageIndex == 1) {
            mDelicacyIntroduceList.clear()
        }
        this.totalCount = totalCount
        mDelicacyIntroduceList.addAll(mList)
        mDelicacyIntroduceAdapter.notifyDataSetChanged()
        mDelicacyIntroduceAdapter.loadMoreComplete()
        swipeDelicacyIntroduce.isRefreshing = false
        nDelicacyIntroduceReq.pageIndex++

    }

    override fun loadDelicacyIntroduceFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyIntroduce.isRefreshing = false
        mDelicacyIntroduceAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyIntroduceFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}