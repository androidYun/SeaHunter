package com.sea.user.activity.integral.shop

import android.content.Intent
import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.address.list.AddressListActivity
import com.sea.user.activity.address.list.AddressListItem
import com.sea.user.presenter.address.DefaultAddressContact
import com.sea.user.presenter.address.DefaultAddressPresenter
import com.sea.user.presenter.sea.order.NPlaceOrderModelReq
import com.sea.user.presenter.sea.order.PlaceOrderContact
import com.sea.user.presenter.sea.order.PlaceOrderPresenter
import kotlinx.android.synthetic.main.activity_exchange_shop.*

class ExchangeShopActivity : BaseActivity(), ExchangeShopContact.IExchangeShopView, PlaceOrderContact.IPlaceOrderView,
    DefaultAddressContact.IDefaultAddressView {

    private val mExchangeShopPresenter by lazy { ExchangeShopPresenter().apply { attachView(this@ExchangeShopActivity) } }

    private val nPlaceOrderPresenter by lazy { PlaceOrderPresenter().apply { attachView(this@ExchangeShopActivity) } }

    private val nPlaceOrderModelReq = NPlaceOrderModelReq()

    private val defaultAddresspresenter by lazy { DefaultAddressPresenter().apply { attachView(this@ExchangeShopActivity) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_shop)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mExchangeShopPresenter.loadExchangeShop(NExchangeShopModelReq())
    }

    private fun initListener() {
        cvSelectAddress.setOnClickListener {
            startActivityForResult(Intent(this, AddressListActivity::class.java), select_address_request_code)
        }
    }

    override fun loadExchangeShopSuccess(content: Any) {


    }

    override fun loadExchangeShopFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPlaceOrderSuccess(orderNo: String) {

    }

    override fun loadPlaceOrderFail(throwable: Throwable) {

    }

    override fun loadDefaultAddressSuccess(addressListItem: AddressListItem) {
        tvNamePhoneNumber.text = addressListItem.accept_name.plus("  ${addressListItem.mobile}")
        tvDetailAddress.text =
            addressListItem.province + addressListItem.city + addressListItem.area + addressListItem.address
        nPlaceOrderModelReq.address_id = addressListItem.id
    }

    override fun loadDefaultAddressFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == select_address_request_code && resultCode == AddressListActivity.select_address_result_code) {
            val addressListItem = data?.extras?.getParcelable(AddressListActivity.select_address_result_key)?:AddressListItem()
            tvNamePhoneNumber.text = addressListItem.accept_name.plus("  ${addressListItem.mobile}")
            tvDetailAddress.text =
                addressListItem.province + addressListItem.city + addressListItem.area + addressListItem.address
            nPlaceOrderModelReq.address_id = addressListItem.id
        }
    }

    companion object {
        const val select_address_request_code = 100
        fun getInstance() = Bundle().apply { }
    }
}