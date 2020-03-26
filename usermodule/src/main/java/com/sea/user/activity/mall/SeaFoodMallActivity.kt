package com.sea.user.activity.mall

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.user.R
import com.sea.user.activity.base.BaseSeaUserActivity
import com.sea.user.activity.mall.adapter.FoodFineAdapter
import com.sea.user.activity.mall.adapter.FoodRecommendAdapter
import com.sea.user.activity.mall.adapter.FoodTypeAdapter
import com.sea.user.activity.mall.adapter.KindFoodAdapter
import com.sea.user.activity.mall.list.MallListActivity
import com.sea.user.activity.mall.search.SearchMallActivity
import com.sea.user.activity.mall.select.SelectStoreActivity
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.sea.user.utils.sp.StoreShopSpUtils
import kotlinx.android.synthetic.main.activity_sea_food_mall.*

class SeaFoodMallActivity : BaseSeaUserActivity(), SeaFoodMallContact.ISeaFoodMallView {

    private val mSeaFoodMallPresenter by lazy { SeaFoodMallPresenter().apply { attachView(this@SeaFoodMallActivity) } }


    private lateinit var mKindFoodAdapter: KindFoodAdapter

    private lateinit var mFoodTypeAdapter: FoodTypeAdapter

    private lateinit var mFoodRecommendAdapter: FoodRecommendAdapter

    private lateinit var mFoodFineAdapter: FoodFineAdapter


    private val mKindFoodList = mutableListOf<SeaCategoryItemModel>()
    private val mTypeFoodList = mutableListOf(
        NFoodType(R.mipmap.bg_mall_project1, R.mipmap.ic_sea_type_1,"必吃榜单", "吃货大本营就在这里"),
        NFoodType(R.mipmap.bg_mall_project2, R.mipmap.ic_sea_type_2,"最新上市", "新品先抢吃"),
        NFoodType(R.mipmap.bg_mall_project3, R.mipmap.ic_sea_type_3,"限时热卖", "买到就是赚到"),
        NFoodType(R.mipmap.bg_mall_project4, R.mipmap.ic_sea_type_4,"精品推荐", "品质生活必备")

    )
    private val mRecommendFoodList = mutableListOf<MallListItem>()
    private val mFineFoodList = mutableListOf<MallListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sea_food_mall)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        selectTab("点餐")
        //商品种类
        mKindFoodAdapter = KindFoodAdapter(mKindFoodList)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rvKindFood.layoutManager = layoutManager
        rvKindFood.adapter = mKindFoodAdapter

        //商品类型
        mFoodTypeAdapter = FoodTypeAdapter(mTypeFoodList)
        rvFoodType.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFoodType.adapter = mFoodTypeAdapter

        //商品推荐
        mFoodRecommendAdapter = FoodRecommendAdapter(mRecommendFoodList)
        rvRecommend.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvRecommend.adapter = mFoodRecommendAdapter

        //精品商品
        mFoodFineAdapter = FoodFineAdapter(mFineFoodList)
        rvFineFood.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFineFood.adapter = mFoodFineAdapter
    }

    private fun initData() {
        mSeaFoodMallPresenter.loadSeaCategory()
        mSeaFoodMallPresenter.loadMallListRecommend(
            NMallListModelReq(
                page_index = 1,
                page_size = 6,
                type = 1
            )
        )
        mSeaFoodMallPresenter.loadMallListHot(
            NMallListModelReq(
                page_index = 1,
                page_size = 6,
                type = 1
            )
        )
    }

    private fun initListener() {

        tvStoreName.setOnClickListener {
            startActivityForResult(
                Intent(this, SelectStoreActivity::class.java),
                select_store_name_key
            )
        }
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(this, SearchMallActivity::class.java),
                select_store_name_key
            )
        }
        swipeSeaFoodMall.setOnRefreshListener {
            initData()
        }
        tvRecommend.setOnClickListener {
            startActivity(Intent(this, MallListActivity::class.java).apply {
                putExtras(MallListActivity.getInstance(isHot = 1))
            })
        }
        tvFineFood.setOnClickListener {
            startActivity(Intent(this, MallListActivity::class.java).apply {
                putExtras(MallListActivity.getInstance(isRed = 1))
            })
        }
    }

    override fun loadSeaCategoryListSuccess(seaCategoryItemModelList: List<SeaCategoryItemModel>) {
        mKindFoodList.clear()
        mKindFoodList.addAll(seaCategoryItemModelList)
        mKindFoodAdapter.notifyDataSetChanged()
        swipeSeaFoodMall.isRefreshing = false
    }

    override fun loadSeaFoodMallFail(throwable: Throwable) {
        handleError(throwable)
        swipeSeaFoodMall.isRefreshing = false
    }

    override fun loadMallListRecommendSuccess(seaCategoryItemModelList: List<MallListItem>) {
        mRecommendFoodList.clear()
        mRecommendFoodList.addAll(seaCategoryItemModelList)
        mFoodRecommendAdapter.notifyDataSetChanged()
        swipeSeaFoodMall.isRefreshing = false
    }

    override fun loadMallListHotSuccess(seaCategoryItemModelList: List<MallListItem>) {
        mFineFoodList.clear()
        mFineFoodList.addAll(seaCategoryItemModelList)
        mFoodFineAdapter.notifyDataSetChanged()
        swipeSeaFoodMall.isRefreshing = false
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
            select_store_name_key -> {
                if (resultCode == SelectStoreActivity.select_store_name_result_key) {
                    tvStoreName.text = StoreShopSpUtils.getStoreShopName()
                }

            }
            else -> {

            }
        }
    }

    companion object {
        const val select_store_name_key = 100
        fun getInstance() = Bundle().apply { }
    }
}