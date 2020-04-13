package com.sea.custom.ui.delicacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_delicacy_layout.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyFragment : BaseFragment(), DelicacyContact.IDelicacyView {

    private val mDelicacyPresenter by lazy { DelicacyPresenter().apply { attachView(this@DelicacyFragment) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_layout, container, false)
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
        mDelicacyPresenter.loadDelicacy(NDelicacyModelReq())
    }

    private fun initListener() {

    }

    override fun loadDelicacySuccess(content: Any) {


    }

    override fun loadDelicacyFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}