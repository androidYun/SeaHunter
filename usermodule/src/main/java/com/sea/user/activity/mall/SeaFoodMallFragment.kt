package com.sea.user.activity.mall

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.user.R
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
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sea_food_mall.*

class SeaFoodMallFragment : BaseFragment(), SeaFoodMallContact.ISeaFoodMallView {

    private val mSeaFoodMallPresenter by lazy { SeaFoodMallPresenter().apply { attachView(this@SeaFoodMallFragment) } }


    private lateinit var mKindFoodAdapter: KindFoodAdapter

    private lateinit var mFoodTypeAdapter: FoodTypeAdapter

    private lateinit var mFoodRecommendAdapter: FoodRecommendAdapter

    private lateinit var mFoodFineAdapter: FoodFineAdapter


    private val mKindFoodList = mutableListOf<SeaCategoryItemModel>()
    private val mTypeFoodList = mutableListOf(
        NFoodType(R.mipmap.bg_mall_project1, R.mipmap.ic_sea_type_1, "必吃榜单", "吃货大本营就在这里"),
        NFoodType(R.mipmap.bg_mall_project2, R.mipmap.ic_sea_type_2, "最新上市", "新品先抢吃"),
        NFoodType(R.mipmap.bg_mall_project3, R.mipmap.ic_sea_type_3, "限时热卖", "买到就是赚到"),
        NFoodType(R.mipmap.bg_mall_project4, R.mipmap.ic_sea_type_4, "精品推荐", "品质生活必备")

    )
    private val mRecommendFoodList = mutableListOf<MallListItem>()
    private val mFineFoodList = mutableListOf<MallListItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sea_food_mall, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        tvStoreName.text = StoreShopSpUtils.getStoreShopName()
        //商品种类
        mKindFoodAdapter = KindFoodAdapter(mKindFoodList)
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rvKindFood.layoutManager = layoutManager
        rvKindFood.adapter = mKindFoodAdapter

        //商品类型
        mFoodTypeAdapter = FoodTypeAdapter(mTypeFoodList)
        rvFoodType.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvFoodType.adapter = mFoodTypeAdapter

        //商品推荐
        mFoodRecommendAdapter = FoodRecommendAdapter(mRecommendFoodList)
        rvRecommend.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecommend.adapter = mFoodRecommendAdapter

        //精品商品
        mFoodFineAdapter = FoodFineAdapter(mFineFoodList)
        rvFineFood.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
                Intent(context, SelectStoreActivity::class.java),
                select_store_name_key
            )
        }
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                select_store_name_key
            )
        }
        swipeSeaFoodMall.setOnRefreshListener {
            initData()
        }
        tvRecommend.setOnClickListener {
            startActivity(Intent(context, MallListActivity::class.java).apply {
                putExtras(MallListActivity.getInstance(isHot = 1))
            })
        }
        tvFineFood.setOnClickListener {
            startActivity(Intent(context, MallListActivity::class.java).apply {
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

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (this.view != null) {
            this.view?.visibility = if (menuVisible) View.VISIBLE else View.GONE
        }
    }

    companion object {
        const val select_store_name_key = 100
        fun getInstance() = SeaFoodMallFragment().apply { }
    }
}