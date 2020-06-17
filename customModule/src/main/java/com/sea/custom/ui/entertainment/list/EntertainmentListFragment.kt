package com.sea.custom.ui.entertainment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.em.ChannelEnum
import com.sea.custom.holder.RecyclerItemNormalHolder
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
import com.sea.publicmodule.utils.weixin.ShareContentWebpage
import com.sea.publicmodule.utils.weixin.WeixiShareUtil
import com.sea.publicmodule.utils.weixin.WeixinShareManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_entertainment_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class EntertainmentListFragment : BaseFragment(), ChannelContact.IChannelView,
    DelicacyCollectionContact.IDelicacyCollectionView, PraiseShareContact.IPraiseShareView {

    private val nChannelPresenter by lazy {
        ChannelPresenter().apply {
            attachView(
                this@EntertainmentListFragment
            )
        }
    }

    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@EntertainmentListFragment
            )
        }
    }
    private val nChannelModelReq = NChannelModelReq()

    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@EntertainmentListFragment) } }

    private val nPraiseShareModelReq = NPraiseShareModelReq()

    private val categoryId by lazy { arguments?.getInt(channel_key_id) ?: 0 }

    private val mChannelList = mutableListOf<NChannelItem>()

    private lateinit var mEntertainmentListAdapter: EntertainmentListAdapter

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
            .inflate(R.layout.fragment_entertainment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    var linearLayoutManager: LinearLayoutManager? = null

    private fun initView() {
        mEntertainmentListAdapter = EntertainmentListAdapter(mChannelList)
        mEntertainmentListAdapter.emptyView =
            emptyView
        linearLayoutManager = LinearLayoutManager(context)
        rvEntertainmentList.layoutManager = linearLayoutManager
        rvEntertainmentList.adapter = mEntertainmentListAdapter
    }

    private fun initData() {
        nChannelModelReq.category_id = categoryId
        nChannelModelReq.channel_name = ChannelEnum.arder.name
        nChannelPresenter.loadChannel(nChannelModelReq)
    }

    private fun initListener() {
        swipeEntertainmentList.setOnRefreshListener {
            nChannelModelReq.page_index = 1
            nChannelPresenter.loadChannel(nChannelModelReq)
        }
        mEntertainmentListAdapter.setOnLoadMoreListener({
            if (nChannelModelReq.page_index * nChannelModelReq.page_size < totalCount) {
                nChannelPresenter.loadChannel(nChannelModelReq)
            } else {
                mEntertainmentListAdapter.loadMoreEnd()
            }
        }, rvEntertainmentList)
        mEntertainmentListAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.rgbCollection -> {
                    if (mChannelList[position].is_collect == false) {
                        mDelicacyCollectionPresenter.loadDelicacyCollection(
                            NDelicacyCollectionModelReq(
                                channel_name = ChannelEnum.arder.name,
                                article_id = mChannelList[position].id ?: -1
                            )
                        )
                    } else {
                        mDelicacyCollectionPresenter.cancelDelicacyCollection(
                            NCancelDelicacyCollectionModelReq(
                                channel_name = ChannelEnum.arder.name,
                                article_id = mChannelList[position].id ?: -1
                            )
                        )
                    }
                }
                R.id.rgbPraise -> {
                    nPraiseShareModelReq.channel_name = ChannelEnum.arder.name
                    nPraiseShareModelReq.article_id = mChannelList[position].id ?: -1
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
                                CommonParamsUtils.articleId = mChannelList[position].id ?: -1
                                CommonParamsUtils.channelName = ChannelEnum.arder.name
                                wsm.shareByWeixin(
                                    ShareContentWebpage(
                                        mChannelList[position].title,
                                        mChannelList[position].zhaiyao ?: "",
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


        var firstVisibleItem = 0
        var lastVisibleItem = 0
        rvEntertainmentList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView!!, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView!!, dx, dy)
                firstVisibleItem = linearLayoutManager!!.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager!!.findLastVisibleItemPosition()
                //大于0说明有播放
                if (GSYVideoManager.instance().playPosition >= 0) { //当前播放的位置
                    val position = GSYVideoManager.instance().playPosition
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().playTag == RecyclerItemNormalHolder.TAG && (position < firstVisibleItem || position > lastVisibleItem)) { //如果滑出去了上面和下面就是否，和今日头条一样
//是否全屏
                        if (!GSYVideoManager.isFullState(activity)) {
                            GSYVideoManager.releaseAllVideos()
                            mEntertainmentListAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

    }


    override fun loadChannelSuccess(mList: List<NChannelItem>, totalCount: Int) {
        if (nChannelModelReq.page_index == 1) {
            mChannelList.clear()
        }
        this.totalCount = totalCount
        mChannelList.addAll(mList)
        mEntertainmentListAdapter.notifyDataSetChanged()
        mEntertainmentListAdapter.loadMoreComplete()
        swipeEntertainmentList.isRefreshing = false
        nChannelModelReq.page_index++
    }

    override fun loadChannelFail(throwable: Throwable) {
        handleError(throwable)
        swipeEntertainmentList.isRefreshing = false
        mEntertainmentListAdapter.loadMoreComplete()
    }

    override fun loadDelicacyCollectionSuccess() {
        nChannelModelReq.page_index = 1
        nChannelPresenter.loadChannel(nChannelModelReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }


    override fun loadPraiseShareSuccess(content: Any) {
        nChannelModelReq.page_index = 1
        nChannelPresenter.loadChannel(nChannelModelReq)
    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }


    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume(false)
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
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


    fun onBackPressed(): Boolean {
        return GSYVideoManager.backFromWindowFull(activity)
    }

    companion object {

        private const val channel_key_id = "channel_key_id"

        fun getInstance(categoryId: Int = 0) = EntertainmentListFragment().apply {
            arguments = Bundle().apply {
                putInt(channel_key_id, categoryId)
            }
        }
    }
}