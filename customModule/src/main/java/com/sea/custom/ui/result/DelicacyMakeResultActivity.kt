package com.sea.custom.ui.result

import android.app.AlertDialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.dialog.CustomServicesDialog
import com.sea.custom.em.ChannelEnum
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.ApplyMembershipContact
import com.sea.custom.presenter.apply.ApplyMembershipPresenter
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.sea.custom.presenter.apply.NApplyMembershipReq
import com.sea.custom.presenter.channel.ChannelContact
import com.sea.custom.presenter.channel.ChannelPresenter
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.NChannelModelReq
import com.sea.custom.presenter.collection.DelicacyCollectionContact
import com.sea.custom.presenter.collection.DelicacyCollectionPresenter
import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter
import com.sea.custom.ui.make.list.DelicacyMakeListAdapter
import com.sea.publicmodule.activity.model.MessageEvent
import com.sea.publicmodule.activity.model.ShareMessageEvent
import com.sea.publicmodule.common.CommonParamsUtils
import com.sea.publicmodule.dialog.ShareCallBack
import com.sea.publicmodule.dialog.WxDialog
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.sea.publicmodule.utils.weixin.ShareContentWebpage
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.sea.publicmodule.utils.weixin.WeixinShareManager
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_search_result.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DelicacyMakeResultActivity : BaseActivity(), ChannelContact.IChannelView,
    PraiseShareContact.IPraiseShareView,
    DelicacyCollectionContact.IDelicacyCollectionView, ApplyMembershipContact.IApplyMembershipView {
    private val nChannelModelReq = NChannelModelReq(
        channel_name = ChannelEnum.food.name
    )
    private val mDelicacyMakeListList = mutableListOf<NChannelItem>()

    private lateinit var mDelicacyMakeListAdapter: DelicacyMakeListAdapter

    private val nApplyMembershipReq = NApplyMembershipReq()

    private var mApplyShipDialog: CustomServicesDialog? = null

    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@DelicacyMakeResultActivity
            )
        }
    }
    private val nPraiseShareModelReq = NPraiseShareModelReq()
    private val mApplyMembershipPresenter by lazy {
        ApplyMembershipPresenter().apply {
            attachView(
                this@DelicacyMakeResultActivity
            )
        }
    }

    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@DelicacyMakeResultActivity) } }


    private val searchContent by lazy { intent.extras?.getString(search_result_key) ?: "" }
    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@DelicacyMakeResultActivity
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        EventBus.getDefault().unregister(this)
        initView()
        initData()
        initListener()
    }

    private fun initListener() {
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
                    if (mDelicacyMakeListList[position].group_id ?: 0 > UserInformSpUtils.getUserInformModel().group_id) {
                        AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("由于您的等级不够，暂时不能定制，请联系门店升级。")
                            .setPositiveButton(
                                "确定"
                            ) { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                        return@setOnItemChildClickListener
                    }
                    nApplyMembershipReq.article_id = mDelicacyMakeListList[position].id ?: 0
                    nApplyMembershipReq.channel_name = ChannelEnum.food.name
                    mApplyShipDialog =
                        CustomServicesDialog(this, object : ApplyMemberShipListener {
                            override fun applyMemberShipSuccess(nApplyMemberModel: NApplyMemberModel) {
                                nApplyMembershipReq.name = nApplyMemberModel.name
                                nApplyMembershipReq.phone = nApplyMemberModel.phone
                                nApplyMembershipReq.address = nApplyMemberModel.address
                                mApplyMembershipPresenter.loadApplyMembership(
                                    nApplyMembershipReq
                                )
                            }
                        })
                    mApplyShipDialog?.show()
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
                    if (!WeixiShareUtil.isWxAppInstalledAndSupported(this)) {
                        ToastUtils.show("请安装微信")
                        return@setOnItemChildClickListener
                    }
                    WxDialog(this, object : ShareCallBack {
                        override fun shareWxSuccess(shareType: Int) {
                            val wsm =
                                WeixinShareManager.getInstance(this@DelicacyMakeResultActivity)
                            CommonParamsUtils.articleId = mDelicacyMakeListList[position].id ?: -1
                            CommonParamsUtils.channelName = ChannelEnum.food.name
                            wsm.shareByWeixin(
                                ShareContentWebpage(
                                    mDelicacyMakeListList[position].title,
                                    mDelicacyMakeListList[position].zhaiyao ?: "",
                                    Constants.shareUrl, R.mipmap.logo
                                ),
                                shareType
                            )
                        }
                    }).show()

                }
            }
        }
    }

    private fun initData() {
        nChannelModelReq.key = searchContent
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
    }

    private fun initView() {
        swipeSearchResult.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            nChannelPresenter.loadChannel(
                nChannelModelReq
            )
        }
        mDelicacyMakeListAdapter = DelicacyMakeListAdapter(mDelicacyMakeListList)
        mDelicacyMakeListAdapter.emptyView =
            emptyView
        rvSearchResult.layoutManager =
            LinearLayoutManager(this)
        rvSearchResult.adapter = mDelicacyMakeListAdapter
    }

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        mDelicacyMakeListList.clear()
        mDelicacyMakeListList.addAll(mList)
        mDelicacyMakeListAdapter.notifyDataSetChanged()
        swipeSearchResult.isRefreshing = false
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeSearchResult.isRefreshing = false
    }


    override fun loadDelicacyCollectionSuccess() {
        nChannelModelReq.page_index = 1
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadApplyMembershipSuccess() {
        ToastUtils.show("申请成功,等待审核")
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
        mApplyShipDialog?.dismiss()
    }

    override fun loadApplyMembershipFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPraiseShareSuccess(content: Any) {
        nChannelModelReq.page_index = 1
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        handleError(throwable)
    }

    /**
     * 分享成功加载列表
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ShareMessageEvent) {
        nChannelModelReq.page_index = 1
        nChannelPresenter.loadChannel(
            nChannelModelReq
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val search_result_key = "search_result_key"
        fun getInstance(searchContent: String = "") = Bundle().apply {
            putString(search_result_key, searchContent)

        }
    }
}