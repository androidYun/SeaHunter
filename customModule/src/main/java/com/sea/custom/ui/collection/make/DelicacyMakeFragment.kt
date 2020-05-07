package com.sea.custom.ui.collection.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.ui.collection.CollectionContact
import com.sea.custom.ui.collection.CollectionItem
import com.sea.custom.ui.collection.CollectionPresenter
import com.sea.custom.ui.collection.NCollectionModelReq
import kotlinx.android.synthetic.main.fragment_activity_delicacy_make.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyMakeFragment : BaseFragment(), CollectionContact.ICollectionView {

    private val mCollectionPresenter by lazy { CollectionPresenter().apply { attachView(this@DelicacyMakeFragment) } }

    private val nDelicacyMakeReq = NCollectionModelReq()

    private val mDelicacyMakeList = mutableListOf<CollectionItem>()

    private lateinit var mDelicacyMakeAdapter: DelicacyMakeAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_activity_delicacy_make, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mDelicacyMakeAdapter = DelicacyMakeAdapter(mDelicacyMakeList)
        rvDelicacyMake.layoutManager = LinearLayoutManager(context)
        rvDelicacyMake.adapter = mDelicacyMakeAdapter
    }

    private fun initData() {
        nDelicacyMakeReq.channel_name = ChannelEnum.food.name
        mCollectionPresenter.loadCollection(nDelicacyMakeReq)
    }

    private fun initListener() {
        swipeDelicacyMake.setOnRefreshListener {
            nDelicacyMakeReq.page_index=1
            mCollectionPresenter.loadCollection(nDelicacyMakeReq)
        }
        mDelicacyMakeAdapter.setOnLoadMoreListener({
            if (nDelicacyMakeReq.page_index * nDelicacyMakeReq.page_size < totalCount) {
                mCollectionPresenter.loadCollection(nDelicacyMakeReq)
            } else {
                mDelicacyMakeAdapter.loadMoreEnd()
            }
        }, rvDelicacyMake)
    }

    override fun loadCollectionSuccess(mList: List<CollectionItem>, totalCount: Int) {
        if (nDelicacyMakeReq.page_index == 1) {
            mDelicacyMakeList.clear()
        }
        this.totalCount = totalCount
        mDelicacyMakeList.addAll(mList)
        mDelicacyMakeAdapter.notifyDataSetChanged()
        mDelicacyMakeAdapter.loadMoreComplete()
        swipeDelicacyMake.isRefreshing = false
        nDelicacyMakeReq.page_index++
    }

    override fun loadCollectionFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyMake.isRefreshing = false
        mDelicacyMakeAdapter.loadMoreComplete()
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyMakeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}