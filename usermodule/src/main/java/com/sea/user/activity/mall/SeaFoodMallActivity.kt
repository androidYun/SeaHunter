package com.sea.user.activity.mall

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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


    private val mKindFoodList = mutableListOf<NKindFood>()
    private val mTypeFoodList = mutableListOf<NFoodType>()
    private val mRecommendFoodList = mutableListOf<NFoodRecommend>()
    private val mFineFoodList = mutableListOf<NFoodFine>()

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
        rvKindFood.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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
    }

    override fun loadSeaFoodMallSuccess(content: Any) {


    }

    override fun loadSeaFoodMallFail(throwable: Throwable) {
        handleError(throwable)
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