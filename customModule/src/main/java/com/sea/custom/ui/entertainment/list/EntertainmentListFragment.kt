package com.sea.custom.ui.entertainment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_entertainment_list.*
import com.xhs.baselibrary.base.BaseFragment

class EntertainmentListFragment : BaseFragment(), EntertainmentListContact.IEntertainmentListView {

    private val mEntertainmentListPresenter by lazy {
        EntertainmentListPresenter().apply {
            attachView(
                this@EntertainmentListFragment
            )
        }
    }

    private val nEntertainmentListReq = NEntertainmentListModelReq()

    private val mEntertainmentListList = mutableListOf<EntertainmentListItem>()

    private lateinit var mEntertainmentListAdapter: EntertainmentListAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_entertainment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mEntertainmentListAdapter = EntertainmentListAdapter(mEntertainmentListList)
        rvEntertainmentList.layoutManager = LinearLayoutManager(context)
        rvEntertainmentList.adapter = mEntertainmentListAdapter
    }

    private fun initData() {
        mEntertainmentListPresenter.loadEntertainmentList(nEntertainmentListReq)
    }

    private fun initListener() {
        swipeEntertainmentList.setOnRefreshListener {
            mEntertainmentListPresenter.loadEntertainmentList(nEntertainmentListReq)
        }
        mEntertainmentListAdapter.setOnLoadMoreListener({
            if (nEntertainmentListReq.page_index * nEntertainmentListReq.page_size < totalCount) {
                mEntertainmentListPresenter.loadEntertainmentList(nEntertainmentListReq)
            } else {
                mEntertainmentListAdapter.loadMoreEnd()
            }
        }, rvEntertainmentList)
    }

    override fun loadEntertainmentListSuccess(mList: List<EntertainmentListItem>, totalCount: Int) {
        if (nEntertainmentListReq.page_index == 1) {
            mEntertainmentListList.clear()
        }
        this.totalCount = totalCount
        mEntertainmentListList.addAll(mList)
        mEntertainmentListAdapter.notifyDataSetChanged()
        mEntertainmentListAdapter.loadMoreComplete()
        swipeEntertainmentList.isRefreshing = false
        nEntertainmentListReq.page_index++

    }

    override fun loadEntertainmentListFail(throwable: Throwable) {
        handleError(throwable)
        swipeEntertainmentList.isRefreshing
        mEntertainmentListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = EntertainmentListFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}