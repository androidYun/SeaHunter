package com.sea.user.activity.mall.car

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity
import kotlinx.android.synthetic.main.activity_shop_car.*

class ShopCarActivity : BaseSeaUserActivity(), ShopCarContact.IShopCarView {

    private val mShopCarPresenter by lazy { ShopCarPresenter().apply { attachView(this@ShopCarActivity) } }

    private val nShopCarReq = NShopCarModelReq()

    private val mShopCarList = mutableListOf<ShopCarItem>()

    private lateinit var mShopCarAdapter: ShopCarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_car)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        selectTab("购物车")
        mShopCarAdapter = ShopCarAdapter(mShopCarList)
        rvShopCar.layoutManager = LinearLayoutManager(this)
        rvShopCar.adapter = mShopCarAdapter
    }

    private fun initData() {
        mShopCarPresenter.loadShopCar(nShopCarReq)
    }

    private fun initListener() {
        swipeShopCar.setOnRefreshListener {
            mShopCarPresenter.loadShopCar(nShopCarReq)
        }
    }

    override fun loadShopCarSuccess(mList: List<ShopCarItem>) {
        swipeShopCar.isRefreshing = false
        mShopCarList.clear()
        mShopCarList.addAll(mList)
        mShopCarAdapter.notifyDataSetChanged()

    }

    override fun loadShopCarFail(throwable: Throwable) {
        handleError(throwable)
        swipeShopCar.isRefreshing = false
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}