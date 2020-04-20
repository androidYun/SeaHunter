package com.sea.custom.ui.delicacy.vr.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.fragment_activity_store_vr_list.*
import com.xhs.baselibrary.base.BaseFragment

class StoreVrListFragment : BaseFragment(), StoreVrListContact.IStoreVrListView {

    private val mStoreVrListPresenter by lazy { StoreVrListPresenter().apply { attachView(this@StoreVrListFragment) } }

    private val nStoreVrListReq = NStoreVrModelReq()

    private val mStoreVrListList = mutableListOf<StoreVrItem>()

    private lateinit var mStoreVrListAdapter: StoreVrListAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_activity_store_vr_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mStoreVrListAdapter = StoreVrListAdapter(mStoreVrListList)
       if(DeviceUtils.isTabletDevice()){
           rvStoreVrList.layoutManager =GridLayoutManager(context,2)
       }else{
           rvStoreVrList.layoutManager =  LinearLayoutManager(context)
       }
        rvStoreVrList.adapter = mStoreVrListAdapter
    }

    private fun initData() {
        mStoreVrListPresenter.loadStoreVrList(nStoreVrListReq)
    }

    private fun initListener() {
        swipeStoreVrList.setOnRefreshListener {
            mStoreVrListPresenter.loadStoreVrList(nStoreVrListReq)
        }
        mStoreVrListAdapter.setOnLoadMoreListener({
            if (nStoreVrListReq.pageIndex * nStoreVrListReq.pageSize < totalCount) {
                mStoreVrListPresenter.loadStoreVrList(nStoreVrListReq)
            } else {
                mStoreVrListAdapter.loadMoreEnd()
            }
        }, rvStoreVrList)
    }

    override fun loadStoreVrListSuccess(mList: List<StoreVrItem>, totalCount: Int) {
        if (nStoreVrListReq.pageIndex == 1) {
            mStoreVrListList.clear()
        }
        this.totalCount = totalCount
        mStoreVrListList.addAll(mList)
        mStoreVrListAdapter.notifyDataSetChanged()
        mStoreVrListAdapter.loadMoreComplete()
        swipeStoreVrList.isRefreshing = false
        nStoreVrListReq.pageIndex++

    }

    override fun loadStoreVrListFail(throwable: Throwable) {
        handleError(throwable)
        swipeStoreVrList.isRefreshing=false
        mStoreVrListAdapter.loadMoreComplete()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = StoreVrListFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}