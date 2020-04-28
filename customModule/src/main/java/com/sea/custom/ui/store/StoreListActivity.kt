package com.sea.custom.ui.store

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citylist.CityListSelectActivity
import com.lljjcoder.style.citythreelist.AreaActivity
import com.sea.custom.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_store_list.*


class StoreListActivity : BaseActivity(), StoreListContact.IStoreListView {

    private val mStoreListPresenter by lazy { StoreListPresenter().apply { attachView(this@StoreListActivity) } }

    private val nStoreListReq = NStoreListModelReq()

    private val mStoreListList = mutableListOf<StoreListItem>()

    private lateinit var mStoreListAdapter: StoreListAdapter

    private var totalCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_list)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        val cityConfig = CityConfig.Builder().build()
        mStoreListAdapter = StoreListAdapter(mStoreListList)
        rvStoreList.layoutManager = LinearLayoutManager(this)
        rvStoreList.adapter = mStoreListAdapter
    }

    private fun initData() {
        mStoreListPresenter.loadStoreList(nStoreListReq)
    }

    private fun initListener() {
        swipeStoreList.setOnRefreshListener {
            mStoreListPresenter.loadStoreList(nStoreListReq)
        }
        tvProvince.setOnClickListener {
//            ActivityUtils.getInstance()
//                .showActivity(this@MainActivity, ProvinceActivity::class.java)
        }
        tvCity.setOnClickListener {
            startActivity(Intent(this, CityListSelectActivity::class.java))
        }
        tvArea.setOnClickListener {
            startActivity(Intent(this, AreaActivity::class.java))
        }
    }

    override fun loadStoreListSuccess(mList: List<StoreListItem>, totalCount: Int) {
        this.totalCount = totalCount
        mStoreListList.addAll(mList)
        mStoreListAdapter.notifyDataSetChanged()
        mStoreListAdapter.loadMoreComplete()
        swipeStoreList.isRefreshing = false

    }

    override fun loadStoreListFail(throwable: Throwable) {
        handleError(throwable)
        swipeStoreList.isRefreshing = false
        mStoreListAdapter.loadMoreComplete()
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