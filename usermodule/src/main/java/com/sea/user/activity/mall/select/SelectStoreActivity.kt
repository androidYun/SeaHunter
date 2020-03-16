package com.sea.user.activity.mall.select

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.presenter.store.NStoreListItemModel
import com.sea.user.presenter.store.NStoreListModelReq
import com.sea.user.presenter.store.StoreListContact
import com.sea.user.presenter.store.StoreListPresenter
import kotlinx.android.synthetic.main.activity_select_store.*

class SelectStoreActivity : BaseActivity(), StoreListContact.IStoreListView {

    private val mSelectStorePresenter by lazy { StoreListPresenter().apply { attachView(this@SelectStoreActivity) } }

    private val nSelectStoreReq = NStoreListModelReq()

    private val mSelectStoreList = mutableListOf<NStoreListItemModel>()

    private lateinit var mSelectStoreAdapter: SelectStoreAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_store)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mSelectStoreAdapter = SelectStoreAdapter(mSelectStoreList)
        rvSelectStore.layoutManager = LinearLayoutManager(this)
        rvSelectStore.adapter = mSelectStoreAdapter
    }

    private fun initData() {
        mSelectStorePresenter.loadStoreList(nSelectStoreReq)
    }

    private fun initListener() {
        swipeSelectStore.setOnRefreshListener {
            mSelectStorePresenter.loadStoreList(nSelectStoreReq)
        }
    }

    override fun loadStoreListSuccess(mList: List<NStoreListItemModel>) {
        mSelectStoreList.clear()
        this.totalCount = totalCount
        mSelectStoreList.addAll(mList)
        mSelectStoreAdapter.notifyDataSetChanged()
        swipeSelectStore.isRefreshing = false
    }

    override fun loadStoreListFail(throwable: Throwable) {
        handleError(throwable)
        swipeSelectStore.isRefreshing = false
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