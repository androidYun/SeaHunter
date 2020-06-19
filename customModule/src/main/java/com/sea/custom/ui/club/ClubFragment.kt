package com.sea.custom.ui.club

import android.app.AlertDialog
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
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.dialog.ApplyShipDialog
import com.sea.custom.dialog.CustomServicesDialog
import com.sea.custom.em.ChannelEnum
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.ApplyMembershipContact
import com.sea.custom.presenter.apply.ApplyMembershipPresenter
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.sea.custom.presenter.apply.NApplyMembershipReq
import com.sea.custom.presenter.banner.BannerContact
import com.sea.custom.presenter.banner.BannerItem
import com.sea.custom.presenter.banner.BannerPresenter
import com.sea.custom.presenter.banner.NBannerModelReq
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.presenter.collection.DelicacyCollectionContact
import com.sea.custom.presenter.collection.DelicacyCollectionPresenter
import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter
import com.sea.custom.ui.adapter.ShopBannerAdapter
import com.sea.custom.ui.club.about.AboutClubActivity
import com.sea.custom.ui.club.activity.ClubActivityActivity
import com.sea.custom.ui.club.match.ClubMatchActivity
import com.sea.custom.ui.delicacy.ToDayActivityAdapter
import com.sea.custom.ui.make.list.DelicacyMakeListAdapter
import com.sea.custom.ui.member.MemberCustomActivity
import com.sea.custom.ui.membership.MainMembershipModeAdapter
import com.sea.custom.ui.membership.MembershipModeActivity
import com.sea.custom.ui.result.ClubSearchResultActivity
import com.sea.custom.ui.store.NStoreListModelReq
import com.sea.custom.ui.store.StoreListContact
import com.sea.custom.ui.store.StoreListItem
import com.sea.custom.ui.store.StoreListPresenter
import com.sea.custom.ui.vr.VrDetailActivity
import com.sea.custom.utils.DeviceUtils
import com.sea.publicmodule.activity.search.SearchMallActivity
import com.sea.publicmodule.common.CommonParamsUtils
import com.sea.publicmodule.dialog.ShareCallBack
import com.sea.publicmodule.dialog.WxDialog
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.sea.publicmodule.utils.weixin.ShareContentWebpage
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.sea.publicmodule.utils.weixin.WeixinShareManager
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.ToastUtils
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.ZoomOutPageTransformer
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.fragment_club_layout.*
import kotlinx.android.synthetic.main.include_search_layout.*

class ClubFragment : BaseFragment(), BannerContact.IBannerView, ClubMainContact.IClubView,
    PraiseShareContact.IPraiseShareView,
    DelicacyCollectionContact.IDelicacyCollectionView,
    ApplyMembershipContact.IApplyMembershipView,
    StoreListContact.IStoreListView {


    private val nClubMainPresenter by lazy {
        ClubMainPresenter().apply {
            attachView(
                this@ClubFragment
            )
        }
    }

    private val bannerPresenter by lazy { BannerPresenter().apply { attachView(this@ClubFragment) } }

    private lateinit var mRecommendActivityAdapter: RecommendActivityAdapter

    private val mRecommendList = mutableListOf<NChannelItem>()

    private val nRecommendActivityChannelModelReq = NChannelModelReq(
        channel_name = ChannelEnum.activity.name,
        is_red = 1
    )

    private val nDelicacyMakeChannelModelReq =
        NChannelModelReq(channel_name = ChannelEnum.food.name, category_id = 0)

    /*西沙没事*/
    private val nDelicacyChannelModelReq =
        NChannelModelReq(channel_name = ChannelEnum.dish.name, category_id = 0)
    private lateinit var mToDayActivityAdapter: ToDayActivityAdapter
    private val toDayActivityList = mutableListOf<NChannelItem>()
    /*美食制作*/
    private val mDelicacyMakeListList = mutableListOf<NChannelItem>()
    private lateinit var mDelicacyMakeListAdapter: DelicacyMakeListAdapter

    /*入会*/
    private val nApplyMembershipReq = NApplyMembershipReq()

    private val nCustomServiceReq = NApplyMembershipReq()

    private val mMembershipModeList = mutableListOf<StoreListItem>()

    private lateinit var mMembershipModeAdapter: MainMembershipModeAdapter
    /*收藏*/
    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@ClubFragment
            )
        }
    }
    /*点赞*/
    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@ClubFragment) } }

    private val nPraiseShareModelReq = NPraiseShareModelReq()

    private val mApplyMembershipPresenter by lazy {
        ApplyMembershipPresenter().apply {
            attachView(
                this@ClubFragment
            )
        }
    }
    private val mSelectStorePresenter by lazy { StoreListPresenter().apply { attachView(this@ClubFragment) } }
    private val nStoreListModelReq = NStoreListModelReq()
    private var mApplyShipDialog: ApplyShipDialog? = null
    private var mCustomServicesDialog: CustomServicesDialog? = null


    /*首页banner*/
    private lateinit var shopBannerAdapter: ShopBannerAdapter

    private val mBannerList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_club_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "海钓俱乐部", false)
    }


    private fun initView() {

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

    private fun initData() {
        shopBannerAdapter = ShopBannerAdapter(mBannerList)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setBannerRound2(BannerUtils.dp2px(8f))
            .setIndicator(CircleIndicator(context)).start()

        bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.club.name))
        /*推荐活动*/
        mRecommendActivityAdapter = RecommendActivityAdapter(mRecommendList)
        if (DeviceUtils.isTabletDevice()) {
            rvRecommendAction.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvRecommendAction.layoutManager = LinearLayoutManager(context)
        }
        rvRecommendAction.adapter = mRecommendActivityAdapter
        /*西沙美食*/
        mToDayActivityAdapter = ToDayActivityAdapter(toDayActivityList)
        if (DeviceUtils.isTabletDevice()) {
            rvDelicacy.layoutManager =
                GridLayoutManager(context, 4)
        } else {
            rvDelicacy.layoutManager =
                GridLayoutManager(context, 2)
        }
        rvDelicacy.adapter = mToDayActivityAdapter
        /*美食制作*/
        mDelicacyMakeListAdapter = DelicacyMakeListAdapter(mDelicacyMakeListList)
        rvDelicacyMake.layoutManager = LinearLayoutManager(context)
        rvDelicacyMake.adapter = mDelicacyMakeListAdapter
        /*入会方式*/
        mMembershipModeAdapter = MainMembershipModeAdapter(mMembershipModeList)
        rvStoreList.layoutManager = LinearLayoutManager(context)
        rvStoreList.adapter = mMembershipModeAdapter
        /*请求数据*/
        nClubMainPresenter.loadRecommendActivityClub(nRecommendActivityChannelModelReq)
        nClubMainPresenter.loadDelicacy(nDelicacyChannelModelReq)
        nClubMainPresenter.loadDelicacyMake(nDelicacyMakeChannelModelReq)
        /*入会方式*/
        mSelectStorePresenter.loadStoreList(nStoreListModelReq)

    }

    private val REQUEST_CODE_SCAN = 100
    private fun initListener() {
        swipeLayout.setOnRefreshListener {
            bannerPresenter.loadBanner(NBannerModelReq(channel_name = ChannelEnum.club.name))
            /*请求数据*/
            nRecommendActivityChannelModelReq.page_index = 1
            nDelicacyChannelModelReq.page_index = 1
            nDelicacyMakeChannelModelReq.page_index = 1
            nClubMainPresenter.loadRecommendActivityClub(nRecommendActivityChannelModelReq)
            nClubMainPresenter.loadDelicacy(nDelicacyChannelModelReq)
            nClubMainPresenter.loadDelicacyMake(nDelicacyMakeChannelModelReq)
            /*入会方式*/
            mSelectStorePresenter.loadStoreList(nStoreListModelReq)
        }
        tvClub.setOnClickListener {
            startActivity(Intent(context, AboutClubActivity::class.java))
        }
        tvClubAction.setOnClickListener {
            startActivity(Intent(context, ClubActivityActivity::class.java))
        }
        tvClubMatch.setOnClickListener {
            startActivity(Intent(context, ClubMatchActivity::class.java))
        }
        tvMember.setOnClickListener {
            startActivity(Intent(context, MemberCustomActivity::class.java))
        }
        tvScan.setOnClickListener {
            val intent = Intent(context, CaptureActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
        }
        tvMemberShipMode.setOnClickListener {
            val intent = Intent(context, MembershipModeActivity::class.java)
            startActivity(intent)
        }
        lvSearchShop.setOnClickListener {
            startActivityForResult(
                Intent(context, SearchMallActivity::class.java),
                SearchMallActivity.search_content_request_code
            )
        }
        mMembershipModeAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tvMemberShipMode -> {
                    nApplyMembershipReq.article_id = mMembershipModeList[position].id ?: 0
                    nApplyMembershipReq.channel_name = ChannelEnum.shop.name
                    nApplyMembershipReq.shop_id = 0
                    activity?.let {
                        mApplyShipDialog =
                            ApplyShipDialog(activity!!, object : ApplyMemberShipListener {
                                override fun applyMemberShipSuccess(nApplyMemberModel: NApplyMemberModel) {
                                    nApplyMembershipReq.name = nApplyMemberModel.name
                                    nApplyMembershipReq.phone = nApplyMemberModel.phone
                                    nApplyMembershipReq.webchat = nApplyMemberModel.webchat
                                    nApplyMembershipReq.birthday = nApplyMemberModel.birthday
                                    mApplyMembershipPresenter.loadApplyMembership(
                                        nApplyMembershipReq
                                    )
                                }
                            })
                        mApplyShipDialog?.show()
                    }
                }
            }
        }
        mDelicacyMakeListAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.rgbCollection -> {
                    if (mDelicacyMakeListList[position].is_collect == false) {
                        mDelicacyCollectionPresenter.loadDelicacyCollection(
                            NDelicacyCollectionModelReq(
                                channel_name = ChannelEnum.food.name,
                                article_id = mDelicacyMakeListList[position].id ?: -1
                            )
                        )
                    } else {
                        mDelicacyCollectionPresenter.cancelDelicacyCollection(
                            NCancelDelicacyCollectionModelReq(
                                channel_name = ChannelEnum.food.name,
                                article_id = mDelicacyMakeListList[position].id ?: -1
                            )
                        )
                    }
                }
                R.id.tvDelicacyStatus -> {
                    activity?.let {
                        if (mDelicacyMakeListList[position].group_id ?: 0 > UserInformSpUtils.getUserInformModel().group_id) {
                            AlertDialog.Builder(context).setTitle("提示")
                                .setMessage("由于您的等级不够，暂时不能定制，请联系门店升级。")
                                .setPositiveButton(
                                    "确定"
                                ) { dialog, _ -> dialog.dismiss() }
                                .create()
                                .show()
                            return@setOnItemChildClickListener
                        }
                        nCustomServiceReq.article_id = mDelicacyMakeListList[position].id ?: 0
                        nCustomServiceReq.channel_name = ChannelEnum.food.name
                        mCustomServicesDialog =
                            CustomServicesDialog(activity!!, object : ApplyMemberShipListener {
                                override fun applyMemberShipSuccess(nApplyMemberModel: NApplyMemberModel) {
                                    nCustomServiceReq.name = nApplyMemberModel.name
                                    nCustomServiceReq.phone = nApplyMemberModel.phone
                                    nCustomServiceReq.address = nApplyMemberModel.address
                                    mApplyMembershipPresenter.loadApplyMembership(
                                        nCustomServiceReq
                                    )
                                }
                            })
                        mCustomServicesDialog?.show()
                    }
                }
                R.id.rgbPraise -> {
                    nPraiseShareModelReq.channel_name = ChannelEnum.food.name
                    nPraiseShareModelReq.article_id = mDelicacyMakeListList[position].id ?: -1
                    nPraiseShareModelReq.click_type = 2
                    mPraiseSharePresenter.loadPraiseShare(
                        nPraiseShareModelReq
                    )
                }
                R.id.rgbForward -> {
                    if (!WeixiShareUtil.isWxAppInstalledAndSupported(context)) {
                        ToastUtils.show("请安装微信")
                        return@setOnItemChildClickListener
                    }
                    context?.let {
                        WxDialog(context!!, object : ShareCallBack {
                            override fun shareWxSuccess(shareType: Int) {
                                val wsm = WeixinShareManager.getInstance(context)
                                CommonParamsUtils.articleId =
                                    mDelicacyMakeListList[position].id ?: -1
                                CommonParamsUtils.channelName = ChannelEnum.food.name
                                wsm.shareByWeixin(
                                    ShareContentWebpage(
                                        mDelicacyMakeListList[position].title,
                                        mDelicacyMakeListList[position].zhaiyao,
                                        Constants.shareUrl,
                                        R.mipmap.logo
                                    ),
                                    shareType
                                )
                            }
                        }).show()
                    }

                }
            }
        }
    }


    override fun loadBannerSuccess(data: List<BannerItem>) {
        mBannerList.clear()
        mBannerList.addAll(data.map { it.img_url })
        /*banner*/
        shopBannerAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchMallActivity.search_content_request_code && resultCode == SearchMallActivity.search_content_result_code) {
            val searchContent = data?.getStringExtra(SearchMallActivity.search_content_key) ?: ""
            startActivity(Intent(context, ClubSearchResultActivity::class.java).apply {
                putExtras(
                    ClubSearchResultActivity.getInstance(searchContent)
                )
            })
        } else if (requestCode == REQUEST_CODE_SCAN) {
            if (null != data) {
                val bundle = data.extras ?: return
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val content = data?.getStringExtra(CodeUtils.RESULT_STRING)
                    if (content.isNullOrBlank() || !content.contains("hnzhiling.com", false)) {
                        return
                    }
                    startActivity(Intent(context, VrDetailActivity::class.java).apply {
                        putExtras(
                            VrDetailActivity.getInstance(content, "产品溯源")
                        )
                    })
                }

            }
        }
    }

    override fun loadBannerFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadRecommendActivitySuccess(mList: List<NChannelItem>) {
        mRecommendList.clear()
        mRecommendList.addAll(
            if (!mList.isNullOrEmpty() && mList.size >= 8) {
                mList.subList(0, 8)
            } else mList
        )
        mRecommendActivityAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun loadDelicacySuccess(mList: List<NChannelItem>) {
        toDayActivityList.clear()
        toDayActivityList.addAll(
            if (!mList.isNullOrEmpty() && mList.size >= 8) {
                mList.subList(0, 8)
            } else mList
        )
        mToDayActivityAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun loadDelicacyMakeSuccess(mList: List<NChannelItem>) {
        mDelicacyMakeListList.clear()
        mDelicacyMakeListList.addAll(
            if (!mList.isNullOrEmpty() && mList.size >= 8) {
                mList.subList(0, 8)
            } else mList
        )
        mDelicacyMakeListAdapter.notifyDataSetChanged()
        mDelicacyMakeListAdapter.loadMoreComplete()
        swipeLayout.isRefreshing = false
    }

    override fun loadStoreListSuccess(mList: List<StoreListItem>, totalCount: Int) {
        mMembershipModeList.clear()
        mMembershipModeList.addAll(
            if (!mList.isNullOrEmpty() && mList.size >= 8) {
                mList.subList(0, 8)
            } else mList
        )
        mMembershipModeAdapter.notifyDataSetChanged()
        mMembershipModeAdapter.loadMoreComplete()
        swipeLayout.isRefreshing = false
    }


    override fun loadStoreListFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
        mMembershipModeAdapter.loadMoreComplete()
    }

    override fun loadApplyMembershipSuccess() {
        ToastUtils.show("申请成功,等待审核")
        mSelectStorePresenter.loadStoreList(nStoreListModelReq)
        mApplyShipDialog?.dismiss()
        mCustomServicesDialog?.dismiss()
    }

    override fun loadApplyMembershipFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadDelicacyCollectionSuccess() {
        nClubMainPresenter.loadDelicacyMake(nDelicacyMakeChannelModelReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPraiseShareSuccess(content: Any) {
        nClubMainPresenter.loadDelicacyMake(nDelicacyMakeChannelModelReq)
    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadFail(throwable: Throwable) {
        swipeLayout.isRefreshing = false
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = ClubFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}