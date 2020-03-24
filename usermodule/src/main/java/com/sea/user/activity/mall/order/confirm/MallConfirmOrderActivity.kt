package com.sea.user.activity.mall.order.confirm

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.address.list.AddressListActivity
import com.sea.user.activity.address.list.AddressListItem
import com.sea.user.activity.address.list.NAddressListModelReq
import com.sea.user.activity.mall.car.ShopCarItem
import com.sea.user.presenter.address.DefaultAddressContact
import com.sea.user.presenter.address.DefaultAddressPresenter
import com.sea.user.utils.sp.StoreShopSpUtils
import kotlinx.android.synthetic.main.activity_mall_confirm_order.*

class MallConfirmOrderActivity : BaseActivity(), MallConfirmOrderContact.IMallConfirmOrderView,
    DefaultAddressContact.IDefaultAddressView {

    private val mMallConfirmOrderPresenter by lazy {
        MallConfirmOrderPresenter().apply {
            attachView(
                this@MallConfirmOrderActivity
            )
        }
    }

    private val defaultAddressPresenter by lazy { DefaultAddressPresenter().apply { attachView(this@MallConfirmOrderActivity) } }

    private val nMallConfirmOrderReq = NMallConfirmOrderModelReq()


    private lateinit var mMallConfirmOrderAdapter: MallConfirmOrderAdapter

    private val mCarItemList by lazy {
        intent?.extras?.getParcelableArrayList(car_item_list_key) ?: ArrayList<ShopCarItem>()
    }

    private val allPrice by lazy { intent?.extras?.getString(car_item_all_price_key) ?: "" }
    private val allPoint by lazy { intent?.extras?.getInt(car_item_all_point_key) ?: 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mall_confirm_order)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMallConfirmOrderAdapter = MallConfirmOrderAdapter(mCarItemList)
        rvMallConfirmOrder.layoutManager = LinearLayoutManager(this)
        rvMallConfirmOrder.adapter = mMallConfirmOrderAdapter
    }

    private fun initData() {
        tvOrderTotalPrice.text = allPrice
        tvGiveIntegral.text = allPoint.toString()
        tvOrderStore.text = StoreShopSpUtils.getStoreShopName()
        defaultAddressPresenter.loadDefaultAddress(NAddressListModelReq())
    }

    private fun initListener() {
        cvSelectAddress.setOnClickListener {
            startActivityForResult(
                Intent(this, AddressListActivity::class.java),
                select_address_request_code
            )
        }
    }

    override fun loadMallConfirmOrderSuccess(mList: List<MallConfirmOrderItem>) {
        mMallConfirmOrderAdapter.notifyDataSetChanged()

    }

    override fun loadMallConfirmOrderFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadDefaultAddressSuccess(addressListItem: AddressListItem) {
        tvNamePhoneNumber.text = addressListItem.accept_name.plus("  ${addressListItem.mobile}")
        tvDetailAddress.text =
            addressListItem.province + addressListItem.city + addressListItem.area + addressListItem.address
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
            val addressListItem =
                data?.extras?.getParcelable(AddressListActivity.select_address_result_key)
                    ?: AddressListItem()
            tvNamePhoneNumber.text = addressListItem.accept_name.plus("  ${addressListItem.mobile}")
            tvDetailAddress.text =
                addressListItem.province + addressListItem.city + addressListItem.area + addressListItem.address
        }
    }

    companion object {

        const val select_address_request_code = 100
        private const val car_item_list_key = "car_item_list_key"

        private const val car_item_all_point_key = "car_item_all_point_key"

        private const val car_item_all_price_key = "car_item_all_price_key"
        fun getInstance(mList: ArrayList<ShopCarItem>, allPrice: String, allPoint: Int) =
            Bundle().apply {
                putParcelableArrayList(car_item_list_key, mList)
                putString(car_item_all_price_key, allPrice)
                putInt(car_item_all_point_key, allPoint)
            }
    }
}