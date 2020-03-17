package com.sea.user.activity.mall.detail

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import kotlinx.android.synthetic.main.activity_shop_detail.*

class ShopDetailActivity : BaseActivity(), ShopDetailContact.IShopDetailView {

    private val mShopDetailPresenter by lazy { ShopDetailPresenter().apply { attachView(this@ShopDetailActivity) } }


    private val goodId by lazy { intent.extras?.getInt(good_id_key) ?: -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {
        mShopDetailPresenter.loadShopDetail(NShopDetailModelReq(good_id = goodId))
    }

    private fun initListener() {
        tvJoinShopCar.setOnClickListener {

        }
    }

    override fun loadShopDetailSuccess(content: Any) {


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
        private const val good_id_key = "good_id_key"
        fun getInstance(goodId: Int) = Bundle().apply {
            putInt(good_id_key, goodId)

        }
    }
}