package com.sea.custom.ui.make.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.presenter.collection.DelicacyCollectionContact
import com.sea.custom.presenter.collection.DelicacyCollectionPresenter
import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.ui.result.DelicacyMakeResultActivity
import com.sea.custom.ui.result.XsDelicacyResultActivity
import com.sea.publicmodule.activity.search.SearchMallActivity
import kotlinx.android.synthetic.main.fragment_delicacy_make_list.*
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.include_search_layout.*

class DelicacyMakeListFragment : BaseFragment(), ChannelContact.IChannelView,
    DelicacyCollectionContact.IDelicacyCollectionView {

    private val mChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@DelicacyMakeListFragment
            )
        }
    }

    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@DelicacyMakeListFragment
            )
        }
    }

    private val nChannelModelReq = NChannelModelReq()

    private val categoryId by lazy { arguments?.getInt(channel_key_id) ?: 0 }

    private val mDelicacyMakeListList = mutableListOf<NChannelItem>()

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
        nChannelModelReq.channel_name = ChannelEnum.food.name
        nChannelModelReq.category_id = categoryId
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeDelicacyMakeList.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mDelicacyMakeListAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mDelicacyMakeListAdapter.loadMoreEnd()
            }
        }, rvDelicacyMakeList)
        mDelicacyMakeListAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.rgbCollection -> {
                    if (mDelicacyMakeListList[position].is_collect == false) {
                        mDelicacyCollectionPresenter.loadDelicacyCollection(
                            NDelicacyCollectionModelReq(
                                channel_name = ChannelEnum.food.name,
                                article_id = mDelicacyMakeListList[position].id ?: -1
                            )
                        )
                    } else {
                        mDelicacyCollectionPresenter.cancelDelicacyCollection(
                            NCancelDelicacyCollectionModelReq(
                                channel_name = ChannelEnum.food.name,
                                article_id = mDelicacyMakeListList[position].id ?: -1
                            )
                        )
                    }
                }
            }
        }
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mDelicacyMakeListList.clear()
        }
        this.totalCount = totalCount
        mDelicacyMakeListList.addAll(mList)
        mDelicacyMakeListAdapter.notifyDataSetChanged()
        mDelicacyMakeListAdapter.loadMoreComplete()
        swipeDelicacyMakeList.isRefreshing = false
        nChannelModelReq.page_index++

    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyMakeList.isRefreshing
        mDelicacyMakeListAdapter.loadMoreComplete()
    }

    override fun loadDelicacyCollectionSuccess() {
        nChannelModelReq.page_index = 1
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val channel_key_id = "channel_key_id"

        fun getInstance(categoryId: Int = 0) = DelicacyMakeListFragment().apply {
            arguments = Bundle().apply {
                putInt(channel_key_id, categoryId)
            }
        }
    }
}