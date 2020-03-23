package com.sea.user.activity.feedback.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import kotlinx.android.synthetic.main.fragment_fragment_feed_back_list.*
import com.xhs.baselibrary.base.BaseFragment

class FeedBackListFragment : BaseFragment(), FeedBackListContact.IFeedBackListView {

    private val mFeedBackListPresenter by lazy { FeedBackListPresenter().apply { attachView(this@FeedBackListFragment) } }

    private val nFeedBackListReq = NFeedBackListModelReq()

    private val mFeedBackListList = mutableListOf<FeedBackListItem>()

    private lateinit var mFeedBackListAdapter: FeedBackListAdapter

    private var totalCount = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_fragment_feed_back_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mFeedBackListList.add(FeedBackListItem())
        mFeedBackListAdapter = FeedBackListAdapter(mFeedBackListList)
        rvFeedBackList.layoutManager = LinearLayoutManager(context)
        rvFeedBackList.adapter = mFeedBackListAdapter
    }

    private fun initData() {
        mFeedBackListPresenter.loadFeedBackList(nFeedBackListReq)
    }

    private fun initListener() {
        swipeFeedBackList.setOnRefreshListener {
            mFeedBackListPresenter.loadFeedBackList(nFeedBackListReq)
        }
        mFeedBackListAdapter.setOnLoadMoreListener({
            if (nFeedBackListReq.page_index * nFeedBackListReq.page_size < totalCount) {
                mFeedBackListPresenter.loadFeedBackList(nFeedBackListReq)
            } else {
                mFeedBackListAdapter.loadMoreEnd()
            }
        }, rvFeedBackList)
    }

    override fun loadFeedBackListSuccess(mList: List<FeedBackListItem>, totalCount: Int) {
        if (nFeedBackListReq.page_index == 1) {
            mFeedBackListList.clear()
        }
        this.totalCount = totalCount
        mFeedBackListList.addAll(mList)
        mFeedBackListAdapter.notifyDataSetChanged()
        mFeedBackListAdapter.loadMoreComplete()
        swipeFeedBackList.isRefreshing = false
        nFeedBackListReq.page_index++

    }

    override fun loadFeedBackListFail(throwable: Throwable) {
        handleError(throwable)
        swipeFeedBackList.isRefreshing
        mFeedBackListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = FeedBackListFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}