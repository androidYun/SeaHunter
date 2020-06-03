package com.sea.custom.ui.collection.introduce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.collection.DelicacyCollectionContact
import com.sea.custom.presenter.collection.DelicacyCollectionPresenter
import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter
import com.sea.custom.ui.collection.CollectionContact
import com.sea.custom.ui.collection.CollectionPresenter
import com.sea.custom.ui.collection.NCollectionModelReq
import kotlinx.android.synthetic.main.fragment_activity_delicacy_introduce.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyIntroduceFragment : BaseFragment(), CollectionContact.ICollectionView,
    DelicacyCollectionContact.IDelicacyCollectionView, PraiseShareContact.IPraiseShareView {

    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@DelicacyIntroduceFragment
            )
        }
    }

    private val mCollectionPresenter by lazy { CollectionPresenter().apply { attachView(this@DelicacyIntroduceFragment) } }

    private val nDelicacyMakeReq = NCollectionModelReq()


    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@DelicacyIntroduceFragment) } }

    private val nPraiseShareModelReq = NPraiseShareModelReq()

    private val mDelicacyIntroduceList = mutableListOf<NChannelItem>()

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
        mDelicacyIntroduceAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.rgbCollection -> {
                    mDelicacyCollectionPresenter.cancelDelicacyCollection(
                        NCancelDelicacyCollectionModelReq(
                            channel_name = ChannelEnum.arder.name,
                            article_id = mDelicacyIntroduceList[position].id ?: -1
                        )
                    )
                }
                R.id.rgbPraise -> {
                    nPraiseShareModelReq.channel_name = ChannelEnum.arder.name
                    nPraiseShareModelReq.article_id = mDelicacyIntroduceList[position].id ?: -1
                    nPraiseShareModelReq.click_type = 2
                    mPraiseSharePresenter.loadPraiseShare(
                        nPraiseShareModelReq
                    )
                }
            }
        }
    }

    override fun loadCollectionSuccess(mList: List<NChannelItem>, totalCount: Int) {
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

    override fun loadDelicacyCollectionSuccess() {
        nDelicacyMakeReq.page_index = 1
        mCollectionPresenter.loadCollection(nDelicacyMakeReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }


    override fun loadPraiseShareSuccess(content: Any) {
        nDelicacyMakeReq.page_index = 1
        mCollectionPresenter.loadCollection(nDelicacyMakeReq)
    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        handleError(throwable)
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