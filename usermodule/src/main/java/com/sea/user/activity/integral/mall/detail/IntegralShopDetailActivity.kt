package com.sea.user.activity.integral.mall.detail

import android.content.Intent
import android.os.Bundle
import com.sea.user.R
import com.sea.user.activity.integral.shop.ExchangeShopActivity
import com.sea.user.activity.mall.detail.*
import com.sea.user.presenter.sea.mall.MallListItem
import com.xhs.baselibrary.base.BaseActivity
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_integral_shop_detail.*
import kotlin.math.abs

class IntegralShopDetailActivity : BaseActivity(),
    ShopDetailContact.IShopDetailView {

    private val mShopDetailPresenter by lazy { ShopDetailPresenter().apply { attachView(this@IntegralShopDetailActivity) } }


    private val mMallListItem by lazy {
        intent?.extras?.getParcelable(mall_list_item_key) ?: MallListItem()
    }


    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral_shop_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setIndicator(CircleIndicator(this)).start()
    }

    private fun initData() {
        mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = mMallListItem.id))
    }

    private fun initListener() {
        tvTwoOnceExchange.setOnClickListener {
            startActivity(Intent(this, ExchangeShopActivity::class.java).apply {
                putExtras(
                    ExchangeShopActivity.getInstance(mMallListItem)
                )
            })
        }
    }


    override fun loadShopDetailSuccess(nShopDetailModel: NShopDetailModel) {
        mBannerList.clear()
        mBannerList.addAll(nShopDetailModel.bannerList)
        shopBannerAdapter?.notifyDataSetChanged()
        tvShopName.text = nShopDetailModel.title
        tvShopRemark.text = nShopDetailModel.tags
        tvShopIntegral.text = "${abs(nShopDetailModel.point.toInt())}"
        tvTwoShopIntegral.text = "${abs(nShopDetailModel.point.toInt())}"
        tvExchangeNumber.text = "已兑：${nShopDetailModel.saleNumber}"

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

    companion object {
        private const val mall_list_item_key = "mall_list_item_key"
        fun getInstance(mallListItem: MallListItem) = Bundle().apply {
            putParcelable(mall_list_item_key, mallListItem)
        }
    }
}

