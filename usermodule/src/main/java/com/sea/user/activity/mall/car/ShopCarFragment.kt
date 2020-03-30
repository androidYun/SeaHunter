package com.sea.user.activity.mall.car

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import com.sea.user.activity.mall.order.confirm.MallConfirmOrderActivity
import com.sea.user.presenter.car.ShopCarEditContact
import com.sea.user.presenter.car.ShopCarEditPresenter
import com.sea.user.utils.sp.StoreShopSpUtils
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_shop_car.*

class ShopCarFragment : BaseFragment(), ShopCarContact.IShopCarView,
    ShopCarEditContact.IShopCarEditView {
    private val mShopCarPresenter by lazy { ShopCarPresenter().apply { attachView(this@ShopCarFragment) } }

    private val mShopCarEditPresenter by lazy { ShopCarEditPresenter().apply { attachView(this@ShopCarFragment) } }


    private val mShopCarList = mutableListOf<ShopCarItem>()

    private lateinit var mShopCarAdapter: ShopCarAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mShopCarAdapter = ShopCarAdapter(mShopCarList)
        rvShopCar.layoutManager = LinearLayoutManager(context)
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
            tvShopAllPrice.text = "￥${mShopCarAdapter.getAllPrice()}"
        }
        mShopCarAdapter.setOnItemClickListener { _, _, _ ->
            tvShopAllPrice.text = "￥${mShopCarAdapter.getAllPrice()}"
        }
        tvOnceOrder.setOnClickListener {
            val allPrice = mShopCarAdapter.getAllPrice()
            val allPoint = mShopCarAdapter.getAllPoint()
            val itemList = mShopCarAdapter.getSelectItemList()
            val list = itemList.map {
                ConfirmOrderShopItem(
                    article_id = it.article_id,
                    channel_id = it.channel_id,
                    goods_id = it.goods_id,
                    img_url = it.img_url,
                    quantity = it.quantity,
                    title = it.title,
                    spec_text = it.spec_text,
                    sell_price = it.sell_price
                )
            }
            startActivity(Intent(context, MallConfirmOrderActivity::class.java).apply {
                putExtras(
                    MallConfirmOrderActivity.getInstance(
                        list as ArrayList<ConfirmOrderShopItem>,
                        allPrice,
                        allPoint
                    )
                )
            })
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

    override fun loadShopCarEditSuccess() {
        mShopCarPresenter.loadShopCar(
            NShopCarModelReq(shop_id = StoreShopSpUtils.getStoreShopId())
        )
    }

    override fun loadShopCarEditFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadDeleteShopCarSuccess() {

    }

    override fun loadDeleteShopCarFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (this.view != null) {
            this.view?.visibility = if (menuVisible) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun getInstance() = ShopCarFragment().apply { }
    }
}