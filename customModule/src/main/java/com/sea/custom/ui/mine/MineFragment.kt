package com.sea.custom.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.custom.R
import kotlinx.android.synthetic.main.fragment_mine_layout.*
import com.xhs.baselibrary.base.BaseFragment

class MineFragment : BaseFragment(), MineContact.IMineView {

    private val mMinePresenter by lazy { MinePresenter().apply { attachView(this@MineFragment) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_mine_layout, container, false)
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
        mMinePresenter.loadMine(NMineModelReq())
    }

    private fun initListener() {

    }

    override fun loadMineSuccess(content: Any) {


    }

    override fun loadMineFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = MineFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}