package com.sea.user.activity.mall.order.confirm

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.address.list.AddressListActivity
import com.sea.user.activity.address.list.AddressListItem
import com.sea.user.activity.address.list.NAddressListModelReq
import com.sea.user.activity.mall.car.ConfirmOrderShopItem
import com.sea.user.activity.mall.car.ShopCarItem
import com.sea.user.presenter.address.DefaultAddressContact
import com.sea.user.presenter.address.DefaultAddressPresenter
import com.sea.user.presenter.sea.order.NPlaceOrderModelReq
import com.sea.user.presenter.sea.order.NShopItemMode
import com.sea.user.presenter.sea.order.PlaceOrderContact
import com.sea.user.presenter.sea.order.PlaceOrderPresenter
import com.sea.user.utils.sp.StoreShopSpUtils
import com.sea.user.utils.sp.UserInformSpUtils
import kotlinx.android.synthetic.main.activity_mall_confirm_order.*

class MallConfirmOrderActivity : BaseActivity(),
    DefaultAddressContact.IDefaultAddressView, PlaceOrderContact.IPlaceOrderView {


    private val defaultAddressPresenter by lazy { DefaultAddressPresenter().apply { attachView(this@MallConfirmOrderActivity) } }

    private val nPlaceOrderPresenter by lazy { PlaceOrderPresenter().apply { attachView(this@MallConfirmOrderActivity) } }

    private val nPlaceOrderModelReq = NPlaceOrderModelReq()


    private lateinit var mMallConfirmOrderAdapter: MallConfirmOrderAdapter

    private val mCarItemList by lazy {
        intent?.extras?.getParcelableArrayList(car_item_list_key)
            ?: ArrayList<ConfirmOrderShopItem>()
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
        tvOrderTotalPrice.text = "￥${allPrice}"
        tvGiveIntegral.text = allPoint.toString()
        tvOrderStore.text = StoreShopSpUtils.getStoreShopName()
        tvNeedTotalPrice.text = allPrice
        /*下单参数*/
        nPlaceOrderModelReq.shop_id = StoreShopSpUtils.getStoreShopId()
        nPlaceOrderModelReq.payment_id = PaymentType.AlipayCode
        nPlaceOrderModelReq.pro_List = mCarItemList.map {
            NShopItemMode(
                article_id = it.article_id,
                channel_id = it.channel_id,
                goods_id = it.goods_id,
                quantity = it.quantity,
                shop_id = StoreShopSpUtils.getStoreShopId(),
                user_id = UserInformSpUtils.getUserId()
            )
        }
        defaultAddressPresenter.loadDefaultAddress(NAddressListModelReq())
    }

    private fun initListener() {
        cvSelectAddress.setOnClickListener {
            startActivityForResult(
                Intent(this, AddressListActivity::class.java),
                select_address_request_code
            )
        }
        rgpPayment.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbtAliPay -> {
                    nPlaceOrderModelReq.payment_id = PaymentType.AlipayCode
                }
                R.id.rbtWechat -> {
                    nPlaceOrderModelReq.payment_id = PaymentType.WechantCode
                }
                R.id.rbtBalance -> {
                    nPlaceOrderModelReq.payment_id = PaymentType.BalanceCode
                }
            }
        }
        tvOnceExchange.setOnClickListener {
            nPlaceOrderPresenter.loadPlaceOrder(nPlaceOrderModelReq)
        }
    }


    override fun loadDefaultAddressSuccess(addressListItem: AddressListItem) {
        nPlaceOrderModelReq.address_id = addressListItem.id
        tvNamePhoneNumber.text = addressListItem.accept_name.plus("  ${addressListItem.mobile}")
        tvDetailAddress.text =
            addressListItem.province + addressListItem.city + addressListItem.area + addressListItem.address
    }

    override fun loadDefaultAddressFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPlaceOrderSuccess(order: String) {

    }

    override fun loadPlaceOrderFail(throwable: Throwable) {

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
            nPlaceOrderModelReq.address_id = addressListItem.id
        }
    }

    companion object {

        const val select_address_request_code = 100
        private const val car_item_list_key = "car_item_list_key"

        private const val car_item_all_point_key = "car_item_all_point_key"

        private const val car_item_all_price_key = "car_item_all_price_key"
        fun getInstance(mList: ArrayList<ConfirmOrderShopItem>, allPrice: String, allPoint: Int) =
            Bundle().apply {
                putParcelableArrayList(car_item_list_key, mList)
                putString(car_item_all_price_key, allPrice)
                putInt(car_item_all_point_key, allPoint)
            }
    }
}