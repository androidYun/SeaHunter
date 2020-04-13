package com.sea.custom.ui.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_delicacy_make.*
import com.xhs.baselibrary.base.BaseFragment

class DelicacyMakeFragment : BaseFragment(), DelicacyMakeContact.IDelicacyMakeView {

    private val mDelicacyMakePresenter by lazy { DelicacyMakePresenter().apply { attachView(this@DelicacyMakeFragment) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_delicacy_make, container, false)
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
        mDelicacyMakePresenter.loadDelicacyMake(NDelicacyMakeModelReq())
    }

    private fun initListener() {

    }

    override fun loadDelicacyMakeSuccess(content: Any) {


    }

    override fun loadDelicacyMakeFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = DelicacyMakeFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}