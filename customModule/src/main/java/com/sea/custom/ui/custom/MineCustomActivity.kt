package com.sea.custom.ui.custom

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_mine_custom.*

class MineCustomActivity : BaseActivity(), MineCustomContact.IMineCustomView {

    private val mMineCustomPresenter by lazy { MineCustomPresenter().apply { attachView(this@MineCustomActivity) } }

    private val nMineCustomReq = NMineCustomModelReq()

    private val mMineCustomList = mutableListOf<MineCustomItem>()

    private lateinit var mMineCustomAdapter: MineCustomAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_custom)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMineCustomAdapter = MineCustomAdapter(mMineCustomList)
        if (DeviceUtils.isTabletDevice()) {
            rvMineCustom.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvMineCustom.layoutManager = LinearLayoutManager(this)
        }
        rvMineCustom.adapter = mMineCustomAdapter
    }

    private fun initData() {
        mMineCustomPresenter.loadMineCustom(nMineCustomReq)
    }

    private fun initListener() {
        swipeMineCustom.setOnRefreshListener {
            mMineCustomPresenter.loadMineCustom(nMineCustomReq)
        }
        mMineCustomAdapter.setOnLoadMoreListener({
            if (nMineCustomReq.page_index * nMineCustomReq.page_size < totalCount) {
                mMineCustomPresenter.loadMineCustom(nMineCustomReq)
            } else {
                mMineCustomAdapter.loadMoreEnd()
            }
        }, rvMineCustom)
    }

    override fun loadMineCustomSuccess(mList: List<MineCustomItem>, totalCount: Int) {
        if (nMineCustomReq.page_index == 1) {
            mMineCustomList.clear()
        }
        this.totalCount = totalCount
        mMineCustomList.addAll(mList)
        mMineCustomAdapter.notifyDataSetChanged()
        mMineCustomAdapter.loadMoreComplete()
        swipeMineCustom.isRefreshing = false
        nMineCustomReq.page_index++

    }

    override fun loadMineCustomFail(throwable: Throwable) {
        handleError(throwable)
        swipeMineCustom.isRefreshing
        mMineCustomAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}