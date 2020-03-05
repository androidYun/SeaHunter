package com.sea.user.activity.address.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_address_list.*

class AddressListActivity : BaseActivity(), AddressListContact.IAddressListView {

    private val mAddressListPresenter by lazy { AddressListPresenter().apply { attachView(this@AddressListActivity) } }

    private val nAddressListReq = NAddressListModelReq()

    private val mAddressListList = mutableListOf<AddressListItem>()

    private lateinit var mAddressListAdapter: AddressListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mAddressListAdapter = AddressListAdapter(mAddressListList)
        rvAddressList.layoutManager = LinearLayoutManager(this)
        rvAddressList.adapter = mAddressListAdapter
    }

    private fun initData() {
        mAddressListPresenter.loadAddressList(nAddressListReq)
    }

    private fun initListener() {
        swipeAddressList.setOnRefreshListener {
            mAddressListPresenter.loadAddressList(nAddressListReq)
        }
    }

    override fun loadAddressListSuccess(mList: List<AddressListItem>) {
        swipeAddressList.isRefreshing = false
        mAddressListList.clear()
        mAddressListList.addAll(mList)
        mAddressListAdapter.notifyDataSetChanged()

    }

    override fun loadAddressListFail(throwable: Throwable) {
        handleError(throwable)
        swipeAddressList.isRefreshing = false
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