package com.sea.custom.ui.delicacy.comment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.em.ChannelEnum
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.detail.ChannelDetailContact
import com.sea.custom.presenter.channel.detail.ChannelDetailPresenter
import com.sea.custom.presenter.channel.detail.NChannelDetailModelReq
import com.sea.custom.presenter.collection.DelicacyCollectionContact
import com.sea.custom.presenter.collection.DelicacyCollectionPresenter
import com.sea.custom.presenter.collection.NCancelDelicacyCollectionModelReq
import com.sea.custom.presenter.collection.NDelicacyCollectionModelReq
import com.sea.custom.presenter.comment.*
import com.sea.custom.presenter.praise.NPraiseShareModelReq
import com.sea.custom.presenter.praise.PraiseShareContact
import com.sea.custom.presenter.praise.PraiseSharePresenter
import com.sea.custom.ui.collection.NCollectionModelReq
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.sea.publicmodule.utils.SoftKeyBoardListener
import kotlinx.android.synthetic.main.activity_delicacy_comment.*
import kotlinx.android.synthetic.main.include_work_operator.*


class DelicacyCommentActivity : BaseActivity(), CommentContact.ICommentView,
    ChannelDetailContact.IChannelDetailView, DelicacyCollectionContact.IDelicacyCollectionView,
    PraiseShareContact.IPraiseShareView {

    private val mDelicacyCommentPresenter by lazy { CommentPresenter().apply { attachView(this@DelicacyCommentActivity) } }

    private val nCommentModelReq = NSubmitCommentModelReq()

    private val mChannelDetailPresenter by lazy { ChannelDetailPresenter().apply { attachView(this@DelicacyCommentActivity) } }

    private val nChannelDetailModelReq = NChannelDetailModelReq()


    private val mDelicacyCollectionPresenter by lazy {
        DelicacyCollectionPresenter().apply {
            attachView(
                this@DelicacyCommentActivity
            )
        }
    }

    private val mPraiseSharePresenter by lazy { PraiseSharePresenter().apply { attachView(this@DelicacyCommentActivity) } }

    private val nPraiseShareModelReq = NPraiseShareModelReq()

    private val nCollectionModelReq = NCollectionModelReq()

    private val channelName by lazy { intent?.extras?.getString(channel_name_key) ?: "" }
    private lateinit var mChannelItem: NChannelItem

    private val mCommentItemList = mutableListOf<CommentItem>()

    private lateinit var mDelicacyCommentAdapter: DelicacyCommentAdapter
    var gsyVideoOptionBuilder: GSYVideoOptionBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delicacy_comment)
        initView()
        initData()
        initListener()
        initOptionsBuilder()
    }

    private fun initOptionsBuilder() {
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoader.loadImageWithUrl(
            imageView,
            Constants.baseUrl + mChannelItem.img_url
        )
        gsyVideoPlayer.thumbImageView = imageView
    }


    private fun initView() {
        mChannelItem = intent?.extras?.getParcelable(Channel_Item_key) ?: NChannelItem()
        gsyVideoOptionBuilder = GSYVideoOptionBuilder()
        gsyVideoPlayer.setUp(Constants.baseUrl + mChannelItem.video_src, true, "")
        mDelicacyCommentAdapter = DelicacyCommentAdapter(mCommentItemList)
        rvDelicacyComment.layoutManager = LinearLayoutManager(this)
        rvDelicacyComment.adapter = mDelicacyCommentAdapter
    }

    private fun initData() {
        //详情
        nChannelDetailModelReq.channel_name = channelName
        nChannelDetailModelReq.id = mChannelItem.id ?: -1
        mChannelDetailPresenter.loadChannelDetail(nChannelDetailModelReq = nChannelDetailModelReq)
        //请求评论列表
        nCommentModelReq.channel_name = channelName
        nCommentModelReq.article_id = mChannelItem.id ?: -1
        mDelicacyCommentPresenter.loadComment(
            NCommentModelReq(
                channel_name = channelName,
                article_id = mChannelItem.id ?: -1
            )
        )
        nPraiseShareModelReq.channel_name = channelName
        nPraiseShareModelReq.article_id = mChannelItem.id ?: -1
        nPraiseShareModelReq.click_type = 1
        mPraiseSharePresenter.loadPraiseShare(
            nPraiseShareModelReq
        )
    }

    private fun initListener() {
        val softKeyBoardListener = SoftKeyBoardListener(this)
        softKeyBoardListener.setOnSoftKeyBoardChangeListener(object :
            SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                includeStatus.visibility = View.GONE
                tvSend.visibility = View.VISIBLE
            }

            override fun keyBoardHide(height: Int) {
                nCommentModelReq.parent_id = 0
                includeStatus.visibility = View.VISIBLE
                tvSend.visibility = View.GONE
            }
        })
        mDelicacyCommentAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tvApply -> {
                    nCommentModelReq.parent_id = mCommentItemList[position].id
                    showSoftInputFromWindow(evSendContent)
                }
            }
        }
        tvSend.setOnClickListener {
            nCommentModelReq.content = evSendContent.text.toString()
            if (nCommentModelReq.content.isNullOrBlank()) {
                ToastUtils.show("请填写回复内容")
                return@setOnClickListener
            }
            mDelicacyCommentPresenter.submitComment(nCommentModelReq)
        }
        swipeLayout.setOnRefreshListener {
            mDelicacyCommentPresenter.loadComment(
                NCommentModelReq(
                    channel_name = channelName,
                    article_id = mChannelItem.id ?: -1
                )
            )
            mChannelDetailPresenter.loadChannelDetail(nChannelDetailModelReq = nChannelDetailModelReq)
        }
        rgbCollection.setOnClickListener {
            if (mChannelItem.is_collect == false) {
                mDelicacyCollectionPresenter.loadDelicacyCollection(
                    NDelicacyCollectionModelReq(
                        channel_name = channelName,
                        article_id = mChannelItem.id ?: -1
                    )
                )
            } else {
                mDelicacyCollectionPresenter.cancelDelicacyCollection(
                    NCancelDelicacyCollectionModelReq(
                        channel_name = channelName,
                        article_id = mChannelItem.id ?: -1
                    )
                )
            }
        }
        rgbPraise.setOnClickListener {
            nPraiseShareModelReq.channel_name = channelName
            nPraiseShareModelReq.article_id = mChannelItem.id ?: -1
            nPraiseShareModelReq.click_type = 2
            mPraiseSharePresenter.loadPraiseShare(
                nPraiseShareModelReq
            )
        }
    }

    private fun showSoftInputFromWindow(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val inputManager =
            editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(editText, 0)
    }


    override fun loadCommentSuccess(mList: List<CommentItem>) {
        mCommentItemList.clear()
        mCommentItemList.addAll(mList)
        mDelicacyCommentAdapter.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun submitCommentSuccess() {
        evSendContent.setText("")
        nCommentModelReq.content = ""
        mDelicacyCommentPresenter.loadComment(
            NCommentModelReq(
                channel_name = channelName,
                article_id = mChannelItem.id ?: -1
            )
        )
    }

    override fun loadCommentFail(throwable: Throwable) {
        handleError(throwable)
        swipeLayout.isRefreshing = false
    }

    override fun loadDelicacyCollectionSuccess() {
        mChannelDetailPresenter.loadChannelDetail(nChannelDetailModelReq = nChannelDetailModelReq)
    }

    override fun loadDelicacyCollectionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun loadPraiseShareSuccess(content: Any) {
        mChannelDetailPresenter.loadChannelDetail(nChannelDetailModelReq = nChannelDetailModelReq)

    }

    override fun loadPraiseShareFail(throwable: Throwable) {
        handleError(throwable)
    }


    override fun loadChannelDetailSuccess(mChannelItem: NChannelItem) {
        this.mChannelItem = mChannelItem
        rgbPraise.text = "${mChannelItem?.zan}"
        rgbCollection.text = "${mChannelItem?.collect_num}"
        rgbForward.text = "${mChannelItem?.share}"
        rgbComment.text = "${mChannelItem?.comment_num}"
        rgbCollection.isChecked = mChannelItem.is_collect ?: false
    }

    override fun loadChannelDetailFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun onPause() {
        super.onPause()
        gsyVideoPlayer.onVideoPause();
    }

    override fun onResume() {
        super.onResume()
        gsyVideoPlayer.onVideoResume();
    }

    override fun onBackPressed() { //先返回正常状态
        if (resources.configuration.orientation === ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            gsyVideoPlayer.fullscreenButton.performClick()
            return
        }
        //释放所有
        gsyVideoPlayer.setVideoAllCallBack(null)
        GSYVideoManager.releaseAllVideos()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed()
        } else {
            Handler().postDelayed(Runnable {
                finish()
            }, 500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val channel_name_key = "channel_name_key"
        private const val Channel_Item_key = "Channel_Item_key"
        fun getInstance(
            channelName: String,
            nChannelItem: NChannelItem
        ) = Bundle().apply {
            putString(channel_name_key, channelName)
            putParcelable(Channel_Item_key, nChannelItem)
        }
    }
}