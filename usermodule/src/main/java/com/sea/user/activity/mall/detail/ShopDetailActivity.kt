package com.sea.user.activity.mall.detail

import android.content.Intent
import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.mall.car.NEditShopCarModelReq
import com.sea.user.activity.mall.car.ShopCarActivity
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

class ShopDetailActivity : BaseActivity(), ShopDetailContact.IShopDetailView,
    ShopCarEditContact.IShopCarEditView, PlaceOrderContact.IPlaceOrderView {

    private val mShopDetailPresenter by lazy { ShopDetailPresenter().apply { attachView(this@ShopDetailActivity) } }

    private val mShopCarEditPresenter by lazy { ShopCarEditPresenter().apply { attachView(this@ShopDetailActivity) } }

    private val nPlaceOrderModelReq = NPlaceOrderModelReq()

    private val nPlaceOrderPresenter by lazy { PlaceOrderPresenter().apply { attachView(this@ShopDetailActivity) } }


    private var nShopDetailModel: NShopDetailModel = NShopDetailModel()

    private val nEditShopCarModelReq = NEditShopCarModelReq(shop_id = StoreShopSpUtils.getStoreShopId())

    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()

    private val goodId by lazy { intent.extras?.getInt(good_id_key) ?: -1 }

    private var selectShopSpecDialog: SelectShopSpecDialog? = null

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
        nEditShopCarModelReq.goods_id = goodId
        mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = goodId))
    }

    private fun initListener() {
        tvJoinShopCar.setOnClickListener {
            mShopCarEditPresenter.loadShopCarEdit(
                nEditShopCarModelReq
            )
        }
        tvOnceBuy.setOnClickListener {

        }
        swipeShopDetail.setOnRefreshListener {
            mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = goodId))
        }
        tvSelectShopSpec.setOnClickListener {
            selectShopSpecDialog = SelectShopSpecDialog(
                this,
                nShopDetailModel,
                object : DialogListener.SelectShopSpecListener {
                    override fun selectShopSpecSuccess(number: Int, mList: List<ShopSpecItemSon>) {
                        nEditShopCarModelReq.quantity = number
                        val goods = getGoods(mList)
                        nEditShopCarModelReq.article_id = goods.article_id
                        nEditShopCarModelReq.goods_id = goods.id
                        nEditShopCarModelReq.channel_id = goods.channel_id
                        nEditShopCarModelReq.article_id = goods.article_id

                    }
                })
            selectShopSpecDialog?.show()
        }
    }


    private fun getGoods(mList: List<ShopSpecItemSon>): Good {
        var articleId: String = ""
        mList.forEach { articleId.plus("${it.article_id},") }
        nShopDetailModel.goods.forEach {
            if (it.spec_ids.contains(articleId))
                return it
        }
        return Good()
    }

    override fun loadShopDetailSuccess(
        nShopDetailModel: NShopDetailModel
    ) {
        this.nShopDetailModel = nShopDetailModel
        tvShopName.text = nShopDetailModel.title
        tvRemark.text = nShopDetailModel.tags
        tvPrice.text = nShopDetailModel.sellPrice
        tvShopPrice.text = nShopDetailModel.sellPrice
        tvSaleNumber.text = nShopDetailModel.saleNumber
        mBannerList.clear()
        mBannerList.addAll(nShopDetailModel.bannerList)
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.adapter = shopBannerAdapter
        swipeShopDetail.isRefreshing = false
        nEditShopCarModelReq.channel_id = nShopDetailModel.channelId
    }

    override fun loadShopDetailFail(throwable: Throwable) {
        handleError(throwable)
        swipeShopDetail.isRefreshing = false
    }

    override fun loadPlaceOrderSuccess(order: String) {

    }

    override fun loadPlaceOrderFail(throwable: Throwable) {

    }

    override fun loadShopCarEditSuccess() {
        startActivity(Intent(this, ShopCarActivity::class.java))
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

    companion object {
        private const val good_id_key = "good_id_key"
        fun getInstance(goodId: Int) = Bundle().apply {
            putInt(good_id_key, goodId)

        }
    }
}