package com.sea.user.activity.address

import android.os.Bundle
import android.view.View
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.style.citypickerview.CityPickerView
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_address.*
import com.lljjcoder.citywheel.CityConfig
import com.sea.user.activity.address.list.AddressListItem


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

    private val addressListItem by lazy {
        intent?.extras?.getParcelable(ADDRESS_ITEM_KEY) ?: AddressListItem()
    }

    private val nAddressModelReq = NAddressModelReq()

    //申明对象
    private val mPicker by lazy { CityPickerView() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        val cityConfig = CityConfig.Builder().build()
        mPicker.setConfig(cityConfig)
        mPicker.init(this)
    }

    private fun initData() {
        if (operatorType == EDIT_ADDRESS_CODE) {//编辑的话需要先加载地址
            nAddressModelReq.accept_name = addressListItem.accept_name
            nAddressModelReq.mobile = addressListItem.mobile
            nAddressModelReq.province = addressListItem.province
            nAddressModelReq.city = addressListItem.city
            nAddressModelReq.area = addressListItem.area
            nAddressModelReq.address = addressListItem.address
            nAddressModelReq.is_default = addressListItem.is_default
            evName.setText(nAddressModelReq.accept_name)
            evPhoneNumber.setText(nAddressModelReq.mobile)
            evDetailAddress.setText(nAddressModelReq.address)
            tvSelectAddress.text =
                addressListItem.province.plus(addressListItem.city).plus(addressListItem.area)
            cbSelectDefault.isChecked = nAddressModelReq.is_default == 1
            tvDeleteAddress.visibility = View.VISIBLE//只有编辑的时候才能显示地址
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
                nAddressModelReq.accept_name = name
                nAddressModelReq.mobile = phoneNumber
                nAddressModelReq.address = detailAddress
                nAddressModelReq.is_default = if (isDefaultAddress) 1 else 0
                addressPresenter.loadAddAddress(nAddressModelReq)
            }

        }
        tvSelectAddress.setOnClickListener {
            mPicker.showCityPicker()
        }
        mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(
                province: ProvinceBean?,
                city: CityBean?,
                district: DistrictBean?
            ) {
                nAddressModelReq.province = province?.name ?: ""
                nAddressModelReq.city = city?.name ?: ""
                nAddressModelReq.area = district?.name ?: ""
                tvSelectAddress.text =
                    nAddressModelReq.province.plus(nAddressModelReq.city)
                        .plus(nAddressModelReq.area)
                super.onSelected(province, city, district)
            }
        })
        tvDeleteAddress.setOnClickListener {
            addressPresenter.
        }

    }


    override fun loadAddAddressSuccess() {
        finish()
    }

    override fun loadAddAddressFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun modifyAddressSuccess() {
        finish()
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
        const val ADD_ADDRESS_CODE = 101
        const val EDIT_ADDRESS_CODE = 102
        private const val ADDRESS_OPERATOR_TYPE_KEY = "ADDRESS_OPERATOR_TYPE_KEY"
        private const val ADDRESS_ITEM_KEY = "ADDRESS_ITEM_KEY"
        fun getInstance(
            operatorType: Int = ADD_ADDRESS_CODE,
            addressListItem: AddressListItem = AddressListItem()
        ) = Bundle().apply {
            putInt(ADDRESS_OPERATOR_TYPE_KEY, operatorType)
            putParcelable(ADDRESS_ITEM_KEY, addressListItem)
        }
    }
}