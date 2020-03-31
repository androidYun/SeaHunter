package com.sea.user.activity.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sea.user.R
import com.sea.user.activity.feedback.FeedBackActivity
import com.sea.user.activity.integral.detail.IntegralDetailActivity
import com.sea.user.activity.integral.exchange.ExchangeListActivity
import com.sea.user.activity.integral.mall.IntegralMallActivity
import com.sea.user.activity.login.UserInformModel
import com.sea.user.activity.mall.order.list.ShopOrderListActivity
import com.sea.user.activity.set.SetActivity
import com.sea.user.activity.wallet.MineWalletActivity
import com.sea.user.common.Constants
import com.sea.user.presenter.user.UserInformContact
import com.sea.user.presenter.user.UserInformPresenter
import com.sea.user.utils.DeviceUtils
import com.sea.user.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import kotlinx.android.synthetic.main.fragment_sea_food_mall.*
import kotlinx.android.synthetic.main.fragment_user_center.*


class UserCenterFragment : BaseFragment(), UserInformContact.IUserInformView {

    private val mUserCenterPresenter by lazy { UserInformPresenter().apply { attachView(this@UserCenterFragment) } }


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
        if (!DeviceUtils.isTabletDevice()) {
            tvMineTitle.text = "我的"
        }
    }

    private fun initView() {

    }

    private fun initData() {
        tvUserName.text = UserInformSpUtils.getUserInformModel().nick_name
        mUserCenterPresenter.loadUserInform()
    }

    private fun initListener() {
        swipeUserCenter.setOnRefreshListener {
            mUserCenterPresenter.loadUserInform()
        }
        //设置
        ivSet.setOnClickListener {
            startActivity(Intent(context, SetActivity::class.java))
        }
        //余额
        tvBalance.setOnClickListener {
            startActivity(Intent(context, MineWalletActivity::class.java))
        }
        //积分
        tvIntegral.setOnClickListener {
            startActivity(Intent(context, IntegralDetailActivity::class.java))
        }
        tvLookAllOrder.setOnClickListener {
            startActivity(Intent(this.context, ShopOrderListActivity::class.java))
        }

        //全部订单和其他订单
        tvAllOrder.setOnClickListener {
            startActivity(Intent(context, ShopOrderListActivity::class.java))
        }
        tvWaitPayment.setOnClickListener {
            startActivity(Intent(context, ShopOrderListActivity::class.java).apply {
                putExtras(
                    ShopOrderListActivity.getInstance(1)
                )
            })
        }
        tvWaitDelivery.setOnClickListener {
            startActivity(Intent(context, ShopOrderListActivity::class.java).apply {
                putExtras(
                    ShopOrderListActivity.getInstance(2)
                )
            })
        }
        tvWaitReceived.setOnClickListener {
            startActivity(Intent(context, ShopOrderListActivity::class.java).apply {
                putExtras(
                    ShopOrderListActivity.getInstance(3)
                )
            })
        }
        tvFinish.setOnClickListener {
            startActivity(Intent(context, ShopOrderListActivity::class.java).apply {
                putExtras(
                    ShopOrderListActivity.getInstance(4)
                )
            })
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
            startActivity(Intent(context, FeedBackActivity::class.java))
        }
        tvContactUs.setOnClickListener {
            startActivity(Intent(context, SetActivity::class.java))
        }
    }

    override fun loadUserInformSuccess(userInformModel: UserInformModel) {
        UserInformSpUtils.setUserInformModel(userInformModel)
        tvBalance.text = userInformModel.amount.toString()
        tvIntegral.text = userInformModel.point.toString()
        ImageLoader.loadCircleImageView(
            ivHead,
            Constants.baseUrl.plus(userInformModel.avatar.replace("//", "/"))
        )
        swipeUserCenter.isRefreshing = false
    }

    override fun loadUserInformFail(throwable: Throwable) {
        swipeUserCenter.isRefreshing = false
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (this.view != null) {
            this.view?.visibility = if (menuVisible) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun getInstance() = UserCenterFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}