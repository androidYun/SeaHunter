package com.sea.user.activity.mall.list

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xhs.baselibrary.base.BaseActivity
import com.sea.user.R
import com.sea.user.presenter.sea.mall.MallListContact
import com.sea.user.presenter.sea.mall.MallListItem
import com.sea.user.presenter.sea.mall.MallListPresenter
import com.sea.user.presenter.sea.mall.NMallListModelReq
import com.xhs.baselibrary.utils.UIUtils
import kotlinx.android.synthetic.main.activity_mall_list.*

class MallListActivity : BaseActivity(), MallListContact.IMallListView {

    private val mMallListPresenter by lazy { MallListPresenter().apply { attachView(this@MallListActivity) } }

    private val nMallListReq = NMallListModelReq(page_size = 20, page_index = 1)

    private val mMallListList = mutableListOf<MallListItem>()

    private lateinit var mMallListAdapter: MallListAdapter

    private var totalCount = 0

    private val type = intent?.extras?.getInt(type_key) ?: -1
    private val categoryId = intent?.extras?.getInt(category_Id_key) ?: -1
    private val plate = intent?.extras?.getInt(plate_key) ?: -1
    private val key = intent?.extras?.getString(key_key) ?: ""
    private val isHot = intent?.extras?.getInt(is_hot_key) ?: -1
    private val isRed = intent?.extras?.getInt(is_red_key) ?: -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mall_list)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mMallListAdapter = MallListAdapter(mMallListList)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        rvMallList.layoutManager = layoutManager
        rvMallList.adapter = mMallListAdapter
    }

    private fun initData() {
        nMallListReq.type = type
        nMallListReq.category_id = categoryId
        nMallListReq.plate = plate
        nMallListReq.is_hot = isHot
        nMallListReq.type = type
        nMallListReq.is_red = isRed
        nMallListReq.key = key
        mMallListPresenter.loadMallList(nMallListReq)
    }

    private fun initListener() {
        swipeMallList.setOnRefreshListener {
            mMallListPresenter.loadMallList(nMallListReq)
        }
        mMallListAdapter.setOnLoadMoreListener({
            if (nMallListReq.page_index * nMallListReq.page_size < totalCount) {
                mMallListPresenter.loadMallList(nMallListReq)
            } else {
                mMallListAdapter.loadMoreEnd()
            }
        }, rvMallList)
        tvDefault.setOnClickListener {
            nMallListReq.sort = 1
            selectTab(tvDefault)
            mMallListPresenter.loadMallList(nMallListReq)
        }
        tvSaleNumber.setOnClickListener {
            nMallListReq.sort = 2
            selectTab(tvSaleNumber)
            mMallListPresenter.loadMallList(nMallListReq)
        }
        tvPrice.setOnClickListener {
            nMallListReq.sort = 3
            selectTab(tvPrice)
            mMallListPresenter.loadMallList(nMallListReq)
        }
    }

    override fun loadMallListSuccess(mList: List<MallListItem>, totalCount: Int) {
        if (nMallListReq.page_index == 1) {
            mMallListList.clear()
        }
        this.totalCount = totalCount
        mMallListList.addAll(mList)
        mMallListAdapter.notifyDataSetChanged()
        mMallListAdapter.loadMoreEnd()
        swipeMallList.isRefreshing = false
        nMallListReq.page_index++

    }

    override fun loadMallListFail(throwable: Throwable) {
        handleError(throwable)
        swipeMallList.isRefreshing
        mMallListAdapter.loadMoreEnd()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    private fun selectTab(textView: TextView) {
        tvDefault.setTextColor(UIUtils.getInstance().getColor(R.color.black))
        tvSaleNumber.setTextColor(UIUtils.getInstance().getColor(R.color.black))
        tvPrice.setTextColor(UIUtils.getInstance().getColor(R.color.black))
        textView.setTextColor(UIUtils.getInstance().getColor(R.color.user_theme_color))
    }

    companion object {

        private const val type_key = "type_key"
        private const val category_Id_key = "category_Id_key"
        private const val plate_key = "plate_key"
        private const val key_key = "key_key"
        private const val is_hot_key = "is_hot_key"
        private const val is_red_key = "is_red_key"

        fun getInstance(
            type: Int = 1,
            categoryId: Int = -1,
            plate: Int = -1,
            key: String = "",
            isHot: Int = 0,
            isRed: Int = 0
        ) = Bundle().apply {
            putInt(type_key, type)
            putInt(category_Id_key, categoryId)
            putInt(plate_key, plate)
            putString(key_key, key)
            putInt(is_hot_key, isHot)
            putInt(is_red_key, isRed)

        }
    }
}