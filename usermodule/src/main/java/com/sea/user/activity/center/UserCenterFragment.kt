package com.sea.user.activity.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.user.R
import com.sea.user.activity.integral.exchange.ExchangeListActivity
import com.sea.user.activity.integral.mall.IntegralMallActivity
import com.sea.user.activity.set.SetActivity
import com.sea.user.activity.shop.order.MineOrderActivity
import com.sea.user.activity.wallet.MineWalletActivity
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_center.*


class UserCenterFragment : BaseFragment(), UserCenterContact.IUserCenterView {

    private val mUserCenterPresenter by lazy { UserCenterPresenter().apply { attachView(this@UserCenterFragment) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_center, container, false)
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
        //设置
        ivSet.setOnClickListener {
            startActivity(Intent(context, SetActivity::class.java))
        }
        //余额
        tvBalance.setOnClickListener {

        }
        //积分
        tvIntegral.setOnClickListener {

        }
        //全部订单和其他订单
        tvAllOrder.setOnClickListener {
            startActivity(Intent(context, MineOrderActivity::class.java))
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
            startActivity(Intent(context, IntegralMallActivity::class.java))
        }
        //积分商城
        tvIntegralMall.setOnClickListener {
            startActivity(Intent(context, IntegralMallActivity::class.java))
        }
        tvMineWallet.setOnClickListener {
            startActivity(Intent(context, MineWalletActivity::class.java))
        }
        tvRecharge.setOnClickListener {
            startActivity(Intent(context, ExchangeListActivity::class.java))
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