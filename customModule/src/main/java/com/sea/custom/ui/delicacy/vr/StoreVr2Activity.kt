package com.sea.custom.ui.delicacy.vr

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.ui.delicacy.vr.list.StoreVr2ListAdapter
import com.sea.custom.ui.delicacy.vr.list.StoreVrResultActivity
import com.sea.custom.ui.store.NStoreListModelReq
import com.sea.custom.ui.store.StoreListContact
import com.sea.custom.ui.store.StoreListItem
import com.sea.custom.ui.store.StoreListPresenter
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_store_vr2.*
import kotlinx.android.synthetic.main.include_search_layout.*

class StoreVr2Activity : BaseActivity(), StoreListContact.IStoreListView {

    private val mStoreListPresenter by lazy { StoreListPresenter().apply { attachView(this@StoreVr2Activity) } }

    private val nStoreListReq = NStoreListModelReq()

    private val mStoreListList = mutableListOf<StoreListItem>()

    private lateinit var mStoreVrListAdapter: StoreVr2ListAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_vr2)
        initView()
        initData()
        initListener()
    }



    private fun initView() {
        mStoreVrListAdapter = StoreVr2ListAdapter(mStoreListList)
        mStoreVrListAdapter.emptyView =
            emptyView
        rvStoreList.layoutManager = LinearLayoutManager(this)
        rvStoreList.adapter = mStoreVrListAdapter
    }

    private fun initData() {
        mStoreListPresenter.loadStoreList(nStoreListReq)
    }
    private fun initListener() {
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(this, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
        swipeStoreList.setOnRefreshListener {
            mStoreListPresenter.loadStoreList(nStoreListReq)
        }
    }



    override fun loadStoreListSuccess(mList: List<StoreListItem>, totalCount: Int) {
        this.totalCount = totalCount
        mStoreListList.clear()
        mStoreListList.addAll(mList)
        mStoreVrListAdapter.notifyDataSetChanged()
        mStoreVrListAdapter.loadMoreComplete()
        swipeStoreList.isRefreshing = false

    }

    override fun loadStoreListFail(throwable: Throwable) {
        handleError(throwable)
        swipeStoreList.isRefreshing = false
        mStoreVrListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchMallActivity.search_content_request_code && resultCode == SearchMallActivity.search_content_result_code) {
            val searchContent = data?.getStringExtra(SearchMallActivity.search_content_key) ?: ""
            startActivity(Intent(this, StoreVrResultActivity::class.java).apply {
                putExtras(
                    StoreVrResultActivity.getInstance(searchContent)
                )
            })
        }
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }

}