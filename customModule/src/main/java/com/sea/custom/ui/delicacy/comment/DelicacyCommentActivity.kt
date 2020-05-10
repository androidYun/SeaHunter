package com.sea.custom.ui.delicacy.comment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.presenter.comment.CommentContact
import com.sea.custom.presenter.comment.CommentItem
import com.sea.custom.presenter.comment.CommentPresenter
import com.sea.custom.presenter.comment.NCommentModelReq
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_delicacy_comment.*

class DelicacyCommentActivity : BaseActivity(), CommentContact.ICommentView {

    private val mDelicacyCommentPresenter by lazy { CommentPresenter().apply { attachView(this@DelicacyCommentActivity) } }

    private val channelName by lazy { intent?.extras?.getString(channel_name_key) ?: "" }
    private val articleId by lazy { intent?.extras?.getInt(article_Id_key) ?: -1 }
    private val videoUrl by lazy { intent?.extras?.getString(video_Url_key) ?: "" }

    private val mCommentItemList = mutableListOf<CommentItem>()

    private lateinit var mDelicacyCommentAdapter: DelicacyCommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delicacy_comment)
        initView()
        initData()
    }

    private fun initView() {
        mDelicacyCommentAdapter = DelicacyCommentAdapter(mCommentItemList)
        rvDelicacyComment.layoutManager = LinearLayoutManager(this)
        rvDelicacyComment.adapter = mDelicacyCommentAdapter
    }

    private fun initData() {
        mDelicacyCommentPresenter.loadComment(
            NCommentModelReq(
                channel_name = channelName,
                article_id = articleId
            )
        )
    }

    override fun loadCommentSuccess(mList: List<CommentItem>) {
        mCommentItemList.clear()
        mCommentItemList.addAll(mList)
        mDelicacyCommentAdapter.notifyDataSetChanged()
    }

    override fun submitCommentSuccess() {

    }

    override fun loadCommentFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val channel_name_key = "channel_name_key"
        private const val article_Id_key = "article_Id_key"
        private const val video_Url_key = "video_Url_key"
        fun getInstance(channelName: String, articleId: Int, videoUrl: String) = Bundle().apply {
            putString(channel_name_key, channelName)
            putInt(
                article_Id_key, articleId
            )
            putString(video_Url_key, videoUrl)
        }
    }
}