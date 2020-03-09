package com.sea.user.activity.feedback.complaint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.user.R
import kotlinx.android.synthetic.main.fragment_comolainte_feedback.*
import com.xhs.baselibrary.base.BaseFragment


class ComplaintFeedbackFragment : BaseFragment(), ComplaintFeedbackContact.IComplaintFeedbackView {

    private val mComplaintFeedbackPresenter by lazy { ComplaintFeedbackPresenter().apply { attachView(this@ComplaintFeedbackFragment) } }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_comolainte_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {

    }

    private fun initData() {
        mComplaintFeedbackPresenter.loadComplaintFeedback(NComplaintFeedbackModelReq())
    }

    private fun initListener() {

    }

    override fun loadComplaintFeedbackSuccess(content: Any) {


    }

    override fun loadComplaintFeedbackFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = ComplaintFeedbackFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}