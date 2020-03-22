package com.sea.user.activity.mall.car

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity
import com.sea.user.presenter.car.ShopCarEditContact
import com.sea.user.presenter.car.ShopCarEditPresenter
import com.sea.user.utils.sp.StoreShopSpUtils
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_shop_car.*

class ShopCarActivity : BaseSeaUserActivity(), ShopCarContact.IShopCarView,
    ShopCarEditContact.IShopCarEditView {

    private val mShopCarPresenter by lazy { ShopCarPresenter().apply { attachView(this@ShopCarActivity) } }

    private val mShopCarEditPresenter by lazy { ShopCarEditPresenter().apply { attachView(this@ShopCarActivity) } }


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
        mShopCarPresenter.loadShopCar(
            NShopCarModelReq(shop_id = StoreShopSpUtils.getStoreShopId())
        )
    }

    private fun initListener() {
        swipeShopCar.setOnRefreshListener {
            mShopCarPresenter.loadShopCar(
                NShopCarModelReq(shop_id = StoreShopSpUtils.getStoreShopId())
            )
        }
        tvDelete.setOnClickListener {
            val selectList = mShopCarAdapter.getSelectList()
            if (selectList.isNullOrEmpty()) {
                ToastUtils.show("选择删除的购物车为空")
                return@setOnClickListener
            }
            mShopCarPresenter.loadDeleteShopCar(NDeleteShopCarModelReq(delete_list = selectList))
        }
        cbCarShop.setOnCheckedChangeListener { _, isChecked ->
            mShopCarAdapter.isAllSelect(isChecked)
        }
    }

    override fun loadShopCarSuccess(mList: List<ShopCarItem>) {
        swipeShopCar.isRefreshing = false
        mShopCarList.clear()
        mShopCarList.addAll(mList)
        mShopCarList.add(ShopCarItem())
        mShopCarList.add(ShopCarItem())
        mShopCarList.add(ShopCarItem())
        mShopCarList.add(ShopCarItem())
        mShopCarList.add(ShopCarItem())
        mShopCarAdapter.notifyDataSetChanged()

    }

    override fun loadShopCarFail(throwable: Throwable) {
        handleError(throwable)
        swipeShopCar.isRefreshing = false
    }

    override fun loadShopCarEditSuccess() {

    }

    override fun loadShopCarEditFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadDeleteShopCarSuccess() {

    }

    override fun loadDeleteShopCarFail(throwable: Throwable) {

    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                if (item.title == "编辑") {
                    item.title = "取消"
                    tvDelete.visibility = View.VISIBLE
                } else {
                    item.title = "编辑"
                    tvDelete.visibility = View.GONE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}