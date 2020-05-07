package com.sea.custom.ui.collection.introduce

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
import kotlinx.android.synthetic.main.fragment_activity_delicacy_introduce.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyIntroduceFragment : BaseFragment(), CollectionContact.ICollectionView {

    private val mCollectionPresenter by lazy { CollectionPresenter().apply { attachView(this@DelicacyIntroduceFragment) } }

    private val nDelicacyMakeReq = NCollectionModelReq()

    private val mDelicacyIntroduceList = mutableListOf<CollectionItem>()

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
        nDelicacyMakeReq.channel_name = ChannelEnum.arder.name
        mCollectionPresenter.loadCollection(nDelicacyMakeReq)
    }

    private fun initListener() {
        swipeDelicacyIntroduce.setOnRefreshListener {
            nDelicacyMakeReq.page_index = 1
            mCollectionPresenter.loadCollection(nDelicacyMakeReq)
        }
        mDelicacyIntroduceAdapter.setOnLoadMoreListener({
            if (nDelicacyMakeReq.page_index * nDelicacyMakeReq.page_size < totalCount) {
                mCollectionPresenter.loadCollection(nDelicacyMakeReq)
            } else {
                mDelicacyIntroduceAdapter.loadMoreEnd()
            }
        }, rvDelicacyIntroduce)
    }

    override fun loadCollectionSuccess(mList: List<CollectionItem>, totalCount: Int) {
        if (nDelicacyMakeReq.page_index == 1) {
            mDelicacyIntroduceList.clear()
        }
        this.totalCount = totalCount
        mDelicacyIntroduceList.addAll(mList)
        mDelicacyIntroduceAdapter.notifyDataSetChanged()
        mDelicacyIntroduceAdapter.loadMoreComplete()
        swipeDelicacyIntroduce.isRefreshing = false
        nDelicacyMakeReq.page_index++

    }

    override fun loadCollectionFail(throwable: Throwable) {
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