package com.sea.custom.ui.delicacy.introduce

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
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_delicacy_introduce.*

class DelicacyIntroduceFragment : BaseFragment(), ChannelContact.IChannelView {

    private val mChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@DelicacyIntroduceFragment
            )
        }
    }

    private val nChannelModelReq = NChannelModelReq()

    private val mDelicacyIntroduceList = mutableListOf<NChannelItem>()

    private lateinit var mDelicacyIntroduceAdapter: DelicacyIntroduceAdapter

    private val categoryId by lazy { arguments?.getInt(channel_key_id) ?: 0 }

    private var totalCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delicacy_introduce, container, false)
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
        nChannelModelReq.channel_name = ChannelEnum.fish.name
        nChannelModelReq.category_id = categoryId
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeDelicacyIntroduce.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mDelicacyIntroduceAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mDelicacyIntroduceAdapter.loadMoreEnd()
            }
        }, rvDelicacyIntroduce)
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mDelicacyIntroduceList.clear()
        }
        this.totalCount = totalCount
        mDelicacyIntroduceList.addAll(mList)
        mDelicacyIntroduceAdapter.notifyDataSetChanged()
        mDelicacyIntroduceAdapter.loadMoreComplete()
        swipeDelicacyIntroduce.isRefreshing = false
        nChannelModelReq.page_index++
    }


    override fun loadChannelFail(throwable: Throwable) {
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
        private const val channel_key_id = "channel_key_id"

        fun getInstance(categoryId: Int = 0) = DelicacyIntroduceFragment().apply {
            arguments = Bundle().apply {
                putInt(channel_key_id, categoryId)
            }
        }
    }
}