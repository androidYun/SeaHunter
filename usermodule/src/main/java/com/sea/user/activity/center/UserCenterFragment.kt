package com.sea.user.activity.center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.user.R
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_center.*


class UserCenterFragment : BaseFragment(), UserCenterContact.IUserCenterView {

    private val mUserCenterPresenter by lazy { UserCenterPresenter().apply { attachView(this@UserCenterFragment) } }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_user_center, container, false)
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
        mUserCenterPresenter.loadUserCenter(NUserCenterModelReq())
    }

    private fun initListener() {
        //余额
        tvBalance.setOnClickListener {

        }
        //积分
        tvIntegral.setOnClickListener {

        }
        //全部订单和其他订单
        tvAllOrder.setOnClickListener {

        }
        tvWaitPayment.setOnClickListener {

        }
        tvWaitDelivery.setOnClickListener {

        }
        tvWaitReceived.setOnClickListener {

        }
        tvFinish.setOnClickListener {

        }
        //下面商城Item
        tvSeaFoodMall.setOnClickListener {

        }
        tvIntegralMall.setOnClickListener {

        }
        tvMineWallet.setOnClickListener {

        }
        tvRecharge.setOnClickListener {

        }
        tvFeedback.setOnClickListener {

        }
        tvContactUs.setOnClickListener {

        }
    }

    override fun loadUserCenterSuccess(content: Any) {


    }

    override fun loadUserCenterFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = UserCenterFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}