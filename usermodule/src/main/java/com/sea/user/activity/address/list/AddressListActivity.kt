package com.sea.user.activity.address.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.address.AddAddressActivity
import kotlinx.android.synthetic.main.activity_address_list.*

class AddressListActivity : BaseActivity(), AddressListContact.IAddressListView {

    private val mAddressListPresenter by lazy { AddressListPresenter().apply { attachView(this@AddressListActivity) } }

    private val nAddressListReq = NAddressListModelReq()

    private val mAddressList = mutableListOf<AddressListItem>()

    private lateinit var mAddressListAdapter: AddressListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mAddressListAdapter = AddressListAdapter(mAddressList)
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
        tvAddAddress.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
        mAddressListAdapter.setOnItemClickListener { _, _, position ->
            setResult(select_address_result_code, Intent().apply {
                putExtras(Bundle().apply {
                    putParcelable(select_address_result_key, mAddressList[position])
                })
            })
            finish()
        }
    }

    override fun loadAddressListSuccess(mList: List<AddressListItem>) {
        swipeAddressList.isRefreshing = false
        mAddressList.clear()
        mAddressList.addAll(mList)
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
         const val select_address_result_code = 101
         const val select_address_result_key = "select_address_result_key"
        fun getInstance() = Bundle().apply { }
    }
}