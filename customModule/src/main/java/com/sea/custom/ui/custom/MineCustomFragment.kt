package com.sea.custom.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.utils.DeviceUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine_custom.*

class MineCustomFragment : BaseFragment(), MineCustomContact.IMineCustomView {

    private val mMineCustomPresenter by lazy { MineCustomPresenter().apply { attachView(this@MineCustomFragment) } }

    private val nMineCustomModelReq = NMineCustomModelReq()

    private val mDelicacyMakeList = mutableListOf<MineCustomItem>()

    private lateinit var mMineCustomAdapter: MineCustomAdapter

    private val channelName by lazy { arguments?.getString(channel_name_key) ?: "" }

    private var totalCount = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine_custom, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMineCustomAdapter = MineCustomAdapter(mDelicacyMakeList, channelName)
        mMineCustomAdapter.emptyView =
            emptyView
        rvMineCustom.layoutManager = LinearLayoutManager(context)
        if (DeviceUtils.isTabletDevice()) {
            rvMineCustom.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvMineCustom.layoutManager = LinearLayoutManager(context)
        }
        rvMineCustom.adapter = mMineCustomAdapter
    }

    private fun initData() {
        nMineCustomModelReq.channel_name = channelName
        mMineCustomPresenter.loadMineCustom(nMineCustomModelReq)
    }

    private fun initListener() {
        swipeMineCustom.setOnRefreshListener {
            nMineCustomModelReq.page_index = 1
            mMineCustomPresenter.loadMineCustom(nMineCustomModelReq)
        }
        mMineCustomAdapter.setOnLoadMoreListener({
            if (nMineCustomModelReq.page_index * nMineCustomModelReq.page_size < totalCount) {
                mMineCustomPresenter.loadMineCustom(nMineCustomModelReq)
            } else {
                mMineCustomAdapter.loadMoreEnd()
            }
        }, rvMineCustom)
    }

    override fun loadMineCustomSuccess(mList: List<MineCustomItem>, totalCount: Int) {
        if (nMineCustomModelReq.page_index == 1) {
            mDelicacyMakeList.clear()
        }

        this.totalCount = totalCount
        mDelicacyMakeList.addAll(mList.filter { !it.article?.title.isNullOrBlank() })
        mMineCustomAdapter.notifyDataSetChanged()
        mMineCustomAdapter.loadMoreComplete()
        swipeMineCustom.isRefreshing = false
        nMineCustomModelReq.page_index++
    }

    override fun loadMineCustomFail(throwable: Throwable) {
        handleError(throwable)
        swipeMineCustom.isRefreshing = false
        mMineCustomAdapter.loadMoreComplete()
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }


    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume(false)
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    companion object {
        private const val channel_name_key = "channel_name_key"
        fun getInstance(channelName: String = "") = MineCustomFragment().apply {
            arguments = Bundle().apply {
                putString(channel_name_key, channelName)
            }
        }
    }
}