package com.sea.user.activity.integral.shop

import android.content.Intent
import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.address.list.AddressListActivity
import com.sea.user.activity.address.list.AddressListItem
import com.sea.user.activity.address.list.NAddressListModelReq
import com.sea.user.activity.integral.exchange.ExchangeListActivity
import com.sea.user.activity.mall.detail.NShopDetailModel
import com.sea.user.activity.mall.detail.NShopDetailModelReq
import com.sea.user.activity.mall.detail.ShopDetailContact
import com.sea.user.activity.mall.detail.ShopDetailPresenter
import com.sea.user.common.Constants
import com.sea.user.presenter.address.DefaultAddressContact
import com.sea.user.presenter.address.DefaultAddressPresenter
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.order.NPlaceOrderModelReq
import com.sea.user.presenter.sea.order.NShopItemMode
import com.sea.user.presenter.sea.order.PlaceOrderContact
import com.sea.user.presenter.sea.order.PlaceOrderPresenter
import com.sea.user.utils.sp.StoreShopSpUtils
import com.xhs.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.activity_exchange_shop.*
import kotlin.math.abs

class ExchangeShopActivity : BaseActivity(),
    PlaceOrderContact.IPlaceOrderView,
    DefaultAddressContact.IDefaultAddressView, ShopDetailContact.IShopDetailView {


    private val nPlaceOrderPresenter by lazy { PlaceOrderPresenter().apply { attachView(this@ExchangeShopActivity) } }

    private val mShopDetailPresenter by lazy { ShopDetailPresenter().apply { attachView(this@ExchangeShopActivity) } }

    private val mMallListItem by lazy {
        intent?.extras?.getParcelable(mall_list_item_key) ?: MallListItem()
    }

    private val nShopItemMode = NShopItemMode(
        shop_id = StoreShopSpUtils.getStoreShopId(),
        user_id = UserInformSpUtils.getUserId()
    )

    private val nPlaceOrderModelReq = NPlaceOrderModelReq(payment_id = 9)

    private val defaultAddressPresenter by lazy { DefaultAddressPresenter().apply { attachView(this@ExchangeShopActivity) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_shop)
        initView()
        initData()
        initListener()
    }

    private var quantity = 1
    private fun initView() {
        ImageLoader.loadImageWithUrl(
            ivShopImage,
            Constants.baseUrl.plus(mMallListItem.img_url)
        )
        tvShopName.text = mMallListItem.title
        tvSingleIntegral.text = "${abs(mMallListItem.point)}积分"
        tvNeedIntegral.text = "${abs(mMallListItem.point)}积分"
        tvNeedTotalIntegral.text = "${abs(mMallListItem.point)}积分"
        amountNumber.setGoodsCount(1)
        amountNumber.setGoods_storage(mMallListItem.stock_quantity)

    }

    private fun initData() {
        nShopItemMode.quantity = 1
        defaultAddressPresenter.loadDefaultAddress(NAddressListModelReq())
        mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = mMallListItem.id))
    }

    private fun initListener() {
        cvSelectAddress.setOnClickListener {
            startActivityForResult(
                Intent(this, AddressListActivity::class.java),
                select_address_request_code
            )
        }
        amountNumber.setOnAmountChangeListener { _, amount ->
            quantity = amount
            nShopItemMode.quantity = amount
            tvNeedIntegral.text = "${abs(mMallListItem.point) * amount}积分"
            tvNeedTotalIntegral.text = "${abs(mMallListItem.point) * amount}积分"
        }
        tvOnceExchange.setOnClickListener {
            nPlaceOrderModelReq.pro_List = listOf(nShopItemMode)
            nPlaceOrderPresenter.loadPlaceOrder(nPlaceOrderModelReq)
        }
    }


    override fun loadPlaceOrderSuccess(orderNo: String) {
        startActivity(Intent(this, ExchangeListActivity::class.java))
        finish()
    }

    override fun loadPlaceOrderFail(throwable: Throwable) {
        handleError(throwable)
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

    override fun loadShopDetailSuccess(nShopDetailModel: NShopDetailModel) {
        nShopItemMode.article_id = nShopDetailModel.id
        nShopItemMode.goods_id = 0
        nShopItemMode.channel_id = nShopDetailModel.channelId
    }

    override fun loadShopDetailFail(throwable: Throwable) {
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
            nPlaceOrderModelReq.address_id = addressListItem.id
        }
    }

    companion object {
        const val select_address_request_code = 100
        private const val mall_list_item_key = "mall_list_item_key"
        fun getInstance(mallListItem: MallListItem) = Bundle().apply {
            putParcelable(mall_list_item_key, mallListItem)
        }
    }
}