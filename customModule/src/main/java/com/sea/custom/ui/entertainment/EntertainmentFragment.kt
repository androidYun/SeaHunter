package com.sea.custom.ui.entertainment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_entertainment_layout.*
import com.xhs.baselibrary.base.BaseFragment

class EntertainmentFragment : BaseFragment(), EntertainmentContact.IEntertainmentView {

    private val mEntertainmentPresenter by lazy { EntertainmentPresenter().apply { attachView(this@EntertainmentFragment) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_entertainment_layout, container, false)
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
        mEntertainmentPresenter.loadEntertainment(NEntertainmentModelReq())
    }

    private fun initListener() {

    }

    override fun loadEntertainmentSuccess(content: Any) {


    }

    override fun loadEntertainmentFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = EntertainmentFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}