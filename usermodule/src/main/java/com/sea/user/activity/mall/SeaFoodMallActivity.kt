package com.sea.user.activity.mall

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.activity.mall.adapter.FoodFineAdapter
import com.sea.user.activity.mall.adapter.FoodRecommendAdapter
import com.sea.user.activity.mall.adapter.FoodTypeAdapter
import com.sea.user.activity.mall.adapter.KindFoodAdapter
import com.sea.user.activity.mall.select.SelectStoreActivity
import kotlinx.android.synthetic.main.activity_sea_food_mall.*

class SeaFoodMallActivity : BaseActivity(), SeaFoodMallContact.ISeaFoodMallView {

    private val mSeaFoodMallPresenter by lazy { SeaFoodMallPresenter().apply { attachView(this@SeaFoodMallActivity) } }

    private lateinit var mKindFoodAdapter: KindFoodAdapter

    private lateinit var mFoodTypeAdapter: FoodTypeAdapter

    private lateinit var mFoodRecommendAdapter: FoodRecommendAdapter

    private lateinit var mFoodFineAdapter: FoodFineAdapter


    private val mKindFoodList = mutableListOf(
        NKindFood(R.mipmap.ic_mall_fish, "鱼类"),
        NKindFood(R.mipmap.ic_mall_shrimp, "虾类"),
        NKindFood(R.mipmap.ic_mall_crab, "螃蟹"),
        NKindFood(R.mipmap.ic_mall_squid, "章鱼"),
        NKindFood(R.mipmap.ic_mall_sea_cucumbe, "海参"),
        NKindFood(R.mipmap.ic_mall_clam, "花甲"),
        NKindFood(R.mipmap.ic_mall_oysters, "生蚝"),
        NKindFood(R.mipmap.ic_mall_all, "全部")

    )
    private val mTypeFoodList = mutableListOf(
        NFoodType(R.mipmap.bg_mall_project1, "必吃榜单", "吃货大本营就在这里"),
        NFoodType(R.mipmap.bg_mall_project2, "必吃榜单", "吃货大本营就在这里"),
        NFoodType(R.mipmap.bg_mall_project3, "必吃榜单", "吃货大本营就在这里"),
        NFoodType(R.mipmap.bg_mall_project4, "必吃榜单", "吃货大本营就在这里")

    )
    private val mRecommendFoodList = mutableListOf<NFoodRecommend>(
        NFoodRecommend(),
        NFoodRecommend(),
        NFoodRecommend(),
        NFoodRecommend(),
        NFoodRecommend(),
        NFoodRecommend(),
        NFoodRecommend(),
        NFoodRecommend()

    )
    private val mFineFoodList = mutableListOf(
        NFoodFine(),
        NFoodFine(),
        NFoodFine(),
        NFoodFine(),
        NFoodFine(),
        NFoodFine(),
        NFoodFine()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sea_food_mall)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

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
        mSeaFoodMallPresenter.loadSeaFoodMall(NSeaFoodMallModelReq())
    }

    private fun initListener() {
        tvStoreName.setOnClickListener {
            startActivity(Intent(this, SelectStoreActivity::class.java))
        }
        swipeSeaFoodMall.setOnRefreshListener { }
    }

    override fun loadSeaFoodMallSuccess(content: Any) {
        swipeSeaFoodMall.isRefreshing = false

    }

    override fun loadSeaFoodMallFail(throwable: Throwable) {
        handleError(throwable)
        swipeSeaFoodMall.isRefreshing = false
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