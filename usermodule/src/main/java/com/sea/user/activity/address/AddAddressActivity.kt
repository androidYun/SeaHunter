package com.sea.user.activity.address

import android.os.Bundle
import android.view.View
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_address.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class AddAddressActivity : BaseActivity(), AddAddressContract.IAddAddressView {

    private val addressPresenter by lazy {
        AddAddressPresenter().apply {
            attachView(this@AddAddressActivity)
        }
    }
    //101是添加 102是编辑
    private val operatorType by lazy {
        intent.extras?.getInt(ADDRESS_OPERATOR_TYPE_KEY) ?: ADD_ADDRESS_CODE
    }

    private val addressId by lazy {
        intent.extras?.getInt(ADDRESS_OPERATOR_TYPE_KEY) ?: ADD_ADDRESS_CODE
    }

    private val nAddressModelReq = NAddressModelReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        if (operatorType == EDIT_ADDRESS_CODE) {//编辑的话需要先加载地址
            addressPresenter.loadAddress(addressId)
            tvDeleteAddress.visibility=View.VISIBLE//只有编辑的时候才能显示地址
        }
    }

    private fun initListener() {
        tvSaveAddress.setOnClickListener {
            val name = evName.text.toString()
            val phoneNumber = evPhoneNumber.text.toString()
            val detailAddress = evDetailAddress.text.toString()
            val isDefaultAddress = cbSelectDefault.isChecked
            if (operatorType == EDIT_ADDRESS_CODE) {//编辑的话需要先加载地址
                addressPresenter.modifyAddress(nAddressModelReq)
            } else {
                addressPresenter.loadAddAddress(nAddressModelReq)
            }

        }

    }


    override fun loadAddAddressSuccess() {

    }

    override fun loadAddAddressFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadAddressSuccess() {

    }

    override fun loadAddressFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun modifyAddressSuccess() {

    }

    override fun modifyAddressFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val ADD_ADDRESS_CODE = 101
        private const val EDIT_ADDRESS_CODE = 102
        private const val ADDRESS_OPERATOR_TYPE_KEY = "ADDRESS_OPERATOR_TYPE_KEY"
        private const val ADDRESS_ID_TYPE_KEY = "ADDRESS_ID_TYPE_KEY"
        fun getInstance(operatorType: Int = ADD_ADDRESS_CODE, addressId: Int) = Bundle().apply {
            putInt(ADDRESS_OPERATOR_TYPE_KEY, operatorType)
            putInt(ADDRESS_ID_TYPE_KEY, addressId)
        }
    }
}