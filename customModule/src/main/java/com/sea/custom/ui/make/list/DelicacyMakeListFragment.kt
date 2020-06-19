package com.sea.custom.ui.make.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.sea.publicmodule.activity.model.ShareMessageEvent
import com.sea.publicmodule.common.CommonParamsUtils
import com.sea.publicmodule.dialog.ShareCallBack
import com.sea.publicmodule.dialog.WxDialog
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.sea.publicmodule.utils.weixin.ShareContentWebpage
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.sea.publicmodule.utils.weixin.WeixinShareManager
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_delicacy_make_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DelicacyMakeListFragment : BaseFragment(), ChannelContact.IChannelView,
    PraiseShareContact.IPraiseShareView,
    DelicacyCollectionContact.IDelicacyCollectionView, ApplyMembershipContact.IApplyMembershipView {

    private val mChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@DelicacyMakeListFragment
            )
        }
    }

    private val mApplyMembershipPresenter by lazy {
        ApplyMembershipPresenter().apply {
            attachView(
                this@DelicacyMakeListFragment
            )
        }
    }

    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@DelicacyMakeListFragment
            )
        }
    }

    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@DelicacyMakeListFragment) } }

    private val nPraiseShareModelReq = NPraiseShareModelReq()
    private val nApplyMembershipReq = NApplyMembershipReq()

    private var mApplyShipDialog: CustomServicesDialog? = null
    private val nChannelModelReq = NChannelModelReq()

    private val categoryId by lazy { arguments?.getInt(channel_key_id) ?: 0 }

    private val mDelicacyMakeListList = mutableListOf<NChannelItem>()

    private lateinit var mDelicacyMakeListAdapter: DelicacyMakeListAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_make_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mDelicacyMakeListAdapter = DelicacyMakeListAdapter(mDelicacyMakeListList)
        mDelicacyMakeListAdapter.emptyView =
            emptyView
        rvDelicacyMakeList.layoutManager = LinearLayoutManager(context)
        rvDelicacyMakeList.adapter = mDelicacyMakeListAdapter
    }

    private fun initData() {
        nApplyMembershipReq.channel_name = ChannelEnum.food.name
        nChannelModelReq.channel_name = ChannelEnum.food.name
        nChannelModelReq.category_id = categoryId
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeDelicacyMakeList.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            mChannelPresenter.loadChannel(nChannelModelReq)
        }
        mDelicacyMakeListAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                mChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mDelicacyMakeListAdapter.loadMoreEnd()
            }
        }, rvDelicacyMakeList)
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
                        nApplyMembershipReq.article_id = mDelicacyMakeListList[position].id ?: 0
                        mApplyShipDialog =
                            CustomServicesDialog(activity!!, object : ApplyMemberShipListener {
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

    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mDelicacyMakeListList.clear()
        }
        this.totalCount = totalCount
        mDelicacyMakeListList.addAll(mList)
        mDelicacyMakeListAdapter.notifyDataSetChanged()
        mDelicacyMakeListAdapter.loadMoreComplete()
        swipeDelicacyMakeList.isRefreshing = false
        nChannelModelReq.page_index++

    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyMakeList.isRefreshing
        mDelicacyMakeListAdapter.loadMoreComplete()
    }

    override fun loadDelicacyCollectionSuccess() {
        nChannelModelReq.page_index = 1
        mChannelPresenter.loadChannel(nChannelModelReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadApplyMembershipSuccess() {
        ToastUtils.show("申请成功,等待审核")
        mChannelPresenter.loadChannel(nChannelModelReq)
        mApplyShipDialog?.dismiss()
    }

    override fun loadApplyMembershipFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPraiseShareSuccess(content: Any) {
        nChannelModelReq.page_index = 1
        mChannelPresenter.loadChannel(nChannelModelReq)
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
        mChannelPresenter.loadChannel(nChannelModelReq)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }


    companion object {
        private const val channel_key_id = "channel_key_id"

        fun getInstance(categoryId: Int = 0) = DelicacyMakeListFragment().apply {
            arguments = Bundle().apply {
                putInt(channel_key_id, categoryId)
            }
        }
    }
}