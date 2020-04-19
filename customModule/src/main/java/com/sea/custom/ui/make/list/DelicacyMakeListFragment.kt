package com.sea.custom.ui.make.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_delicacy_make_list.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyMakeListFragment : BaseFragment(), DelicacyMakeListContact.IDelicacyMakeListView {

    private val mDelicacyMakeListPresenter by lazy {
        DelicacyMakeListPresenter().apply {
            attachView(
                this@DelicacyMakeListFragment
            )
        }
    }

    private val nDelicacyMakeListReq = NDelicacyMakeListModelReq()

    private val mDelicacyMakeListList = mutableListOf<DelicacyMakeListItem>()

    private lateinit var mDelicacyMakeListAdapter: DelicacyMakeListAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_make_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mDelicacyMakeListAdapter = DelicacyMakeListAdapter(mDelicacyMakeListList)
        rvDelicacyMakeList.layoutManager = LinearLayoutManager(context)
        rvDelicacyMakeList.adapter = mDelicacyMakeListAdapter
    }

    private fun initData() {
        mDelicacyMakeListPresenter.loadDelicacyMakeList(nDelicacyMakeListReq)
    }

    private fun initListener() {
        swipeDelicacyMakeList.setOnRefreshListener {
            mDelicacyMakeListPresenter.loadDelicacyMakeList(nDelicacyMakeListReq)
        }
        mDelicacyMakeListAdapter.setOnLoadMoreListener({
            if (nDelicacyMakeListReq.pageIndex * nDelicacyMakeListReq.pageSize < totalCount) {
                mDelicacyMakeListPresenter.loadDelicacyMakeList(nDelicacyMakeListReq)
            } else {
                mDelicacyMakeListAdapter.loadMoreEnd()
            }
        }, rvDelicacyMakeList)
    }

    override fun loadDelicacyMakeListSuccess(mList: List<DelicacyMakeListItem>, totalCount: Int) {
        if (nDelicacyMakeListReq.pageIndex == 1) {
            mDelicacyMakeListList.clear()
        }
        this.totalCount = totalCount
        mDelicacyMakeListList.addAll(mList)
        mDelicacyMakeListAdapter.notifyDataSetChanged()
        mDelicacyMakeListAdapter.loadMoreComplete()
        swipeDelicacyMakeList.isRefreshing = false
        nDelicacyMakeListReq.pageIndex++

    }

    override fun loadDelicacyMakeListFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyMakeList.isRefreshing
        mDelicacyMakeListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyMakeListFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}