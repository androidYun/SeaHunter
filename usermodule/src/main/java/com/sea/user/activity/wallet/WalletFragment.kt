package com.sea.user.activity.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sea.user.R
import kotlinx.android.synthetic.main.fragment_wallet.*
import com.xhs.baselibrary.base.BaseFragment


class WalletFragment : BaseFragment(), WalletContact.IWalletView {

    private val mWalletPresenter by lazy { WalletPresenter().apply { attachView(this@WalletFragment) } }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_wallet, container, false)
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
        mWalletPresenter.loadWallet(NWalletModelReq())
    }

    private fun initListener() {

    }

    override fun loadWalletSuccess(content: Any) {


    }

    override fun loadWalletFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        const val WALLET_BALANCE_CODE = 101
        const val WALLET_REFLECT_CODE = 102

        private const val WALLET_TYPE_KEY = "WALLET_TYPE_KEY"
        fun getInstance(walletType: Int = WALLET_BALANCE_CODE) = WalletFragment().apply {
            arguments = Bundle().apply {
                putInt(WALLET_TYPE_KEY, walletType)
            }
        }
    }
}