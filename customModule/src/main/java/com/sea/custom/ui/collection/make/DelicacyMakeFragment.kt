package com.sea.custom.ui.collection.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.collection.DelicacyCollectionContact
import com.sea.custom.presenter.collection.DelicacyCollectionPresenter
import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter
import com.sea.custom.ui.collection.CollectionContact
import com.sea.custom.ui.collection.CollectionPresenter
import com.sea.custom.ui.collection.NCollectionModelReq
import com.sea.publicmodule.activity.model.ShareMessageEvent
import com.sea.publicmodule.common.CommonParamsUtils
import com.sea.publicmodule.dialog.ShareCallBack
import com.sea.publicmodule.dialog.WxDialog
import com.sea.publicmodule.utils.weixin.ShareContentWebpage
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.sea.publicmodule.utils.weixin.WeixinShareManager
import kotlinx.android.synthetic.main.fragment_activity_delicacy_make.*
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DelicacyMakeFragment : BaseFragment(), CollectionContact.ICollectionView,
    DelicacyCollectionContact.IDelicacyCollectionView, PraiseShareContact.IPraiseShareView {


    private val mCollectionPresenter by lazy { CollectionPresenter().apply { attachView(this@DelicacyMakeFragment) } }

    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@DelicacyMakeFragment
            )
        }
    }

    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@DelicacyMakeFragment) } }

    private val nPraiseShareModelReq = NPraiseShareModelReq()


    private val nCollectionModelReq = NCollectionModelReq()

    private val mDelicacyMakeList = mutableListOf<NChannelItem>()

    private lateinit var mDelicacyMakeAdapter: DelicacyMakeAdapter

    private var totalCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EventBus.getDefault().register(this)
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_activity_delicacy_make, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        mDelicacyMakeAdapter = DelicacyMakeAdapter(mDelicacyMakeList)
        mDelicacyMakeAdapter.emptyView =
            emptyView
        rvDelicacyMake.layoutManager = LinearLayoutManager(context)
        rvDelicacyMake.adapter = mDelicacyMakeAdapter
    }

    private fun initData() {
        nCollectionModelReq.channel_name = ChannelEnum.food.name
        mCollectionPresenter.loadCollection(nCollectionModelReq)
    }

    private fun initListener() {
        swipeDelicacyMake.setOnRefreshListener {
            nCollectionModelReq.page_index = 1
            mCollectionPresenter.loadCollection(nCollectionModelReq)
        }
        mDelicacyMakeAdapter.setOnLoadMoreListener({
            if (nCollectionModelReq.page_index * nCollectionModelReq.page_size < totalCount) {
                mCollectionPresenter.loadCollection(nCollectionModelReq)
            } else {
                mDelicacyMakeAdapter.loadMoreEnd()
            }
        }, rvDelicacyMake)
        mDelicacyMakeAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.rgbCollection -> {
                    mDelicacyCollectionPresenter.cancelDelicacyCollection(
                        NCancelDelicacyCollectionModelReq(
                            channel_name = ChannelEnum.food.name,
                            article_id = mDelicacyMakeList[position].id ?: -1
                        )
                    )
                }
                R.id.rgbPraise -> {
                    nPraiseShareModelReq.channel_name = ChannelEnum.food.name
                    nPraiseShareModelReq.article_id = mDelicacyMakeList[position].id ?: -1
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
                                CommonParamsUtils.articleId = mDelicacyMakeList[position].id ?: -1
                                CommonParamsUtils.channelName = ChannelEnum.food.name
                                wsm.shareByWeixin(
                                    ShareContentWebpage(
                                        mDelicacyMakeList[position].title,
                                        mDelicacyMakeList[position].zhaiyao ?: "",
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
    }

    override fun loadCollectionSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nCollectionModelReq.page_index == 1) {
            mDelicacyMakeList.clear()
        }
        this.totalCount = totalCount
        mDelicacyMakeList.addAll(mList)
        mDelicacyMakeAdapter.notifyDataSetChanged()
        mDelicacyMakeAdapter.loadMoreComplete()
        swipeDelicacyMake.isRefreshing = false
        nCollectionModelReq.page_index++
    }

    override fun loadCollectionFail(throwable: Throwable) {
        handleError(throwable)
        swipeDelicacyMake.isRefreshing = false
        mDelicacyMakeAdapter.loadMoreComplete()
    }

    override fun loadDelicacyCollectionSuccess() {
        nCollectionModelReq.page_index = 1
        mCollectionPresenter.loadCollection(nCollectionModelReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPraiseShareSuccess(content: Any) {
        nCollectionModelReq.page_index = 1
        mCollectionPresenter.loadCollection(nCollectionModelReq)
    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        handleError(throwable)
    }

    /**
     * 分享成功加载列表
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ShareMessageEvent) {
        nCollectionModelReq.page_index = 1
        mCollectionPresenter.loadCollection(nCollectionModelReq)
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
        fun getInstance() = DelicacyMakeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}