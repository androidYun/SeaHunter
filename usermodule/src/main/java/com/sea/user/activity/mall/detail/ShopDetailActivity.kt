package com.sea.user.activity.mall.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.mall.SeaFoodMallActivity
import com.sea.user.activity.mall.car.ConfirmOrderShopItem
import com.sea.user.activity.mall.car.NEditShopCarModelReq
import com.sea.user.activity.mall.car.ShopCarActivity
import com.sea.user.activity.mall.car.ShopCarItem
import com.sea.user.activity.mall.order.confirm.MallConfirmOrderActivity
import com.sea.user.activity.mall.select.SelectStoreActivity
import com.sea.user.dialog.SelectShopSpecDialog
import com.sea.user.listener.DialogListener
import com.sea.user.presenter.car.ShopCarEditContact
import com.sea.user.presenter.car.ShopCarEditPresenter
import com.sea.user.presenter.sea.order.NPlaceOrderModelReq
import com.sea.user.presenter.sea.order.PlaceOrderContact
import com.sea.user.presenter.sea.order.PlaceOrderPresenter
import com.sea.user.utils.sp.StoreShopSpUtils
import com.youth.banner.config.IndicatorConfig
import kotlinx.android.synthetic.main.activity_shop_detail.*
import java.math.BigDecimal

class ShopDetailActivity : BaseActivity(), ShopDetailContact.IShopDetailView,
    ShopCarEditContact.IShopCarEditView {

    private val mShopDetailPresenter by lazy { ShopDetailPresenter().apply { attachView(this@ShopDetailActivity) } }

    private val mShopCarEditPresenter by lazy { ShopCarEditPresenter().apply { attachView(this@ShopDetailActivity) } }


    private var nShopDetailModel: NShopDetailModel = NShopDetailModel()

    private val confirmOrderShopItemList = mutableListOf<ConfirmOrderShopItem>()

    var confirmOrderShopItem: ConfirmOrderShopItem = ConfirmOrderShopItem()

    private val nEditShopCarModelReq =
        NEditShopCarModelReq(shop_id = StoreShopSpUtils.getStoreShopId())

    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()

    private val goodId by lazy { intent.extras?.getInt(good_id_key) ?: -1 }

    private var selectShopSpecDialog: SelectShopSpecDialog? = null

    private var shopParamsAdapter: ShopParamsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        bannerView.setIndicatorSelectedColorRes(R.color.user_theme_color)
        bannerView.setIndicatorNormalColorRes(R.color.color_6)
        bannerView.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
    }

    private fun initData() {
        tvStoreName.text = StoreShopSpUtils.getStoreShopName()
        nEditShopCarModelReq.goods_id = goodId
        mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = goodId))
    }

    private fun initListener() {
        tvJoinShopCar.setOnClickListener {
            mShopCarEditPresenter.loadShopCarEdit(
                nEditShopCarModelReq
            )
        }
        tvStoreName.setOnClickListener {
            startActivityForResult(
                Intent(this, SelectStoreActivity::class.java),
                select_store_name_shop_detail_request_code
            )
        }
        tvOnceBuy.setOnClickListener {
            confirmOrderShopItemList.add(confirmOrderShopItem)
            var allPrice = BigDecimal(0)
            var allPoint = 0
            confirmOrderShopItemList.forEach {
                allPrice = it.sell_price.multiply(BigDecimal(it.quantity))
                allPoint = it.point * it.quantity
            }
            startActivity(Intent(this, MallConfirmOrderActivity::class.java).apply {
                putExtras(
                    MallConfirmOrderActivity.getInstance(
                        confirmOrderShopItemList as ArrayList<ConfirmOrderShopItem>,
                        allPrice.toString(),
                        allPoint
                    )
                )
            })
            finish()
        }
        swipeShopDetail.setOnRefreshListener {
            mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = goodId))
        }
        tvSelectShopSpec.setOnClickListener {
            selectShopSpecDialog = SelectShopSpecDialog(
                this,
                nShopDetailModel,
                object : DialogListener.SelectShopSpecListener {
                    override fun selectShopSpecSuccess(number: Int, goods: Good) {
                        if (number > 0) {
                            tvShopCarNumber.visibility = View.VISIBLE
                            tvShopCarNumber.text = "$number"
                            tvShopPrice.text = "${nEditShopCarModelReq.quantity * goods.sell_price}"
                        } else {
                            tvShopCarNumber.visibility = View.GONE
                        }
                        if (goods.goods_no.isNullOrBlank()) {
                            confirmOrderShopItem.quantity = number
                            nEditShopCarModelReq.quantity = number
                            return
                        }
                        tvSelectShopSpec.text = goods.spec_text
                        /**
                         *加载购物车
                         */
                        nEditShopCarModelReq.quantity = number
                        nEditShopCarModelReq.article_id = goods.article_id
                        nEditShopCarModelReq.goods_id = goods.id
                        nEditShopCarModelReq.channel_id = goods.channel_id
                        nEditShopCarModelReq.article_id = goods.article_id
                        /**
                         * 立即购买
                         */
                        confirmOrderShopItem.quantity = number
                        confirmOrderShopItem.spec_text = goods.spec_text
                        confirmOrderShopItem.article_id = goods.article_id
                        confirmOrderShopItem.goods_id = goods.id
                        confirmOrderShopItem.stock_quantity = goods.stock_quantity
                    }
                })
            selectShopSpecDialog?.show()
        }
    }


    override fun loadShopDetailSuccess(
        nShopDetailModel: NShopDetailModel
    ) {
        this.nShopDetailModel = nShopDetailModel
        tvShopName.text = nShopDetailModel.title
        tvRemark.text = nShopDetailModel.tags
        tvPrice.text = nShopDetailModel.sellPrice.setScale(2).toString()
        tvShopPrice.text = nShopDetailModel.sellPrice.setScale(2).toString()
        tvSaleNumber.text = nShopDetailModel.saleNumber
//        /*给要购买的商品赋值*/
        confirmOrderShopItem = ConfirmOrderShopItem(
            article_id = nShopDetailModel.id,
            channel_id = nShopDetailModel.channelId,
            stock_quantity = nShopDetailModel.stockQuantity,
            goods_id = 0,
            img_url = nShopDetailModel.imageUrl,
            quantity = 1,
            title = nShopDetailModel.title,
            spec_text = "",
            point = nShopDetailModel.point.toInt(),
            sell_price = nShopDetailModel.sellPrice
        )
        mBannerList.clear()
        mBannerList.addAll(nShopDetailModel.bannerList)
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.adapter = shopBannerAdapter
        swipeShopDetail.isRefreshing = false
        /*参数设置*/
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rvDetailParams.layoutManager = layoutManager
        shopParamsAdapter = ShopParamsAdapter(nShopDetailModel.paramsList)
        rvDetailParams.adapter = shopParamsAdapter
        nEditShopCarModelReq.channel_id = nShopDetailModel.channelId
        if (nShopDetailModel.specs.isNullOrEmpty()) {
            nEditShopCarModelReq.article_id = nShopDetailModel.id
            nEditShopCarModelReq.goods_id = 0
        }
        webView.loadUrl("https://www.baidu.com/")
    }

    override fun loadShopDetailFail(throwable: Throwable) {
        handleError(throwable)
        swipeShopDetail.isRefreshing = false
    }


    override fun loadShopCarEditSuccess() {
        startActivity(Intent(this, ShopCarActivity::class.java))
        finish()
    }

    override fun loadShopCarEditFail(throwable: Throwable) {
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
        when (requestCode) {
            select_store_name_shop_detail_request_code -> {
                if (resultCode == SelectStoreActivity.select_store_name_result_key) {
                    tvStoreName.text = StoreShopSpUtils.getStoreShopName()
                }
            }
            else -> {

            }
        }
    }

    companion object {
        private const val good_id_key = "good_id_key"
        const val select_store_name_shop_detail_request_code = 200
        fun getInstance(goodId: Int) = Bundle().apply {
            putInt(good_id_key, goodId)

        }
    }
}