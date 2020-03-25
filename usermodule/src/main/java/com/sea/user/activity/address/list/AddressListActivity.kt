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
            startActivityForResult(
                Intent(this, AddAddressActivity::class.java),
                operator_address_request_code
            )
        }
        mAddressListAdapter.setOnItemClickListener { _, view, position ->
            when (view.id) {
                R.id.tvEditAddress -> {
                    startActivityForResult(Intent(this, AddAddressActivity::class.java).apply {
                        putExtras(
                            AddAddressActivity.getInstance(
                                AddAddressActivity.EDIT_ADDRESS_CODE,
                                mAddressList[position]
                            )
                        )
                    }, operator_address_request_code)
                }
                R.id.cvSelectAddress -> {
                    setResult(select_address_result_code, Intent().apply {
                        putExtras(Bundle().apply {
                            putParcelable(select_address_result_key, mAddressList[position])
                        })
                    })
                    finish()
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == operator_address_request_code && resultCode == AddAddressActivity.operator_address_result_code) {
            mAddressListPresenter.loadAddressList(nAddressListReq)
        }
    }

    companion object {
        const val select_address_result_code = 101
        const val operator_address_request_code = 200
        const val select_address_result_key = "select_address_result_key"
        fun getInstance() = Bundle().apply { }
    }
}