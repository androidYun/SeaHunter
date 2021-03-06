package com.sea.user.activity.mall

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.sea.user.R
import com.sea.user.activity.mall.adapter.FoodFineAdapter
import com.sea.user.activity.mall.adapter.FoodRecommendAdapter
import com.sea.user.activity.mall.adapter.FoodTypeAdapter
import com.sea.user.activity.mall.adapter.KindFoodAdapter
import com.sea.user.activity.mall.detail.ShopBannerAdapter
import com.sea.user.activity.mall.list.MallListActivity
import com.sea.user.activity.mall.select.SelectStoreActivity
import com.sea.user.presenter.banner.BannerContact
import com.sea.user.presenter.banner.BannerItem
import com.sea.user.presenter.banner.BannerPresenter
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.sea.user.utils.DeviceUtils
import com.sea.user.utils.sp.StoreShopSpUtils
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sea_food_mall.*


class SeaFoodMallFragment : BaseFragment(), SeaFoodMallContact.ISeaFoodMallView, BannerContact.IBannerView {

    private val mSeaFoodMallPresenter by lazy { SeaFoodMallPresenter().apply { attachView(this@SeaFoodMallFragment) } }

    private val bannerPresenter by lazy { BannerPresenter().apply { attachView(this@SeaFoodMallFragment) } }


    private lateinit var mKindFoodAdapter: KindFoodAdapter

    private lateinit var mFoodTypeAdapter: FoodTypeAdapter

    private lateinit var mFoodRecommendAdapter: FoodRecommendAdapter

    private lateinit var mFoodFineAdapter: FoodFineAdapter
    /*首页banner*/
    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()


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
        if (!DeviceUtils.isTabletDevice()) {
            initToolbar(toolbar, "海鲜商城", false)
        }
    }

    /**
     * Fragment中初始化Toolbar
     * @param toolbar
     * @param title 标题
     * @param isDisplayHomeAsUp 是否显示返回箭头
     */
    private fun initToolbar(toolbar: Toolbar?, title: String?, isDisplayHomeAsUp: Boolean) {
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        val actionBar: ActionBar? = appCompatActivity.supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp)
        }
    }

    private fun initView() {
        tvStoreName.text =
            if (StoreShopSpUtils.getStoreShopName().isNullOrBlank()) "请选择店面" else StoreShopSpUtils.getStoreShopName()
        //商品种类
        if (DeviceUtils.isTabletDevice()) {
            /*种类*/
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.CENTER
            rvKindFood.layoutManager = layoutManager
            /*类型*/
            rvFoodType.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            /*种类*/
            val layoutManager = GridLayoutManager(context, 4)
            /*类型*/
            rvKindFood.layoutManager = layoutManager
            rvFoodType.layoutManager =
                GridLayoutManager(context, 2)
        }
        mKindFoodAdapter = KindFoodAdapter(mKindFoodList)
        rvKindFood.adapter = mKindFoodAdapter

        //商品类型
        mFoodTypeAdapter = FoodTypeAdapter(mTypeFoodList)
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
        bannerPresenter.loadBanner()
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

    override fun loadBannerSuccess(data: List<BannerItem>) {
        mBannerList.clear()
        mBannerList.addAll(data.map { it.img_url })
        /*banner*/
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.adapter = shopBannerAdapter
    }

    override fun loadBannerFail(throwable: Throwable) {
        handleError(throwable)
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