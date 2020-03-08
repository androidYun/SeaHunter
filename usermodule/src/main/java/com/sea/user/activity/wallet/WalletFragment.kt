package com.sea.user.activity.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.user.R
import kotlinx.android.synthetic.main.fragment_wallet.*
import com.xhs.baselibrary.base.BaseFragment


class WalletFragment : BaseFragment(), WalletContact.IWalletView {

    private val mWalletPresenter by lazy { WalletPresenter().apply { attachView(this@WalletFragment) } }

    private val nWalletReq = NWalletModelReq()

    private val mWalletList = mutableListOf<WalletListItem>()

    private lateinit var mWalletListAdapter: WalletListAdapter

    private var totalCount = 0

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
        mWalletListAdapter = WalletListAdapter(mWalletList)
        rvWalletList.layoutManager = LinearLayoutManager(context)
        rvWalletList.adapter = mWalletListAdapter
        mWalletPresenter.loadWallet(NWalletModelReq())
    }

    private fun initListener() {
        swipeWalletList.setOnRefreshListener {
            mWalletPresenter.loadWallet(nWalletReq)
        }
        mWalletListAdapter.setOnLoadMoreListener({
            if (nWalletReq.pageIndex * nWalletReq.pageSize < totalCount) {
                mWalletPresenter.loadWallet(nWalletReq)
            } else {
                mWalletListAdapter.loadMoreEnd()
            }
        }, rvWalletList)
    }

    override fun loadWalletSuccess(mList: List<WalletListItem>, totalCount: Int) {
        if (nWalletReq.pageIndex == 1) {
            mWalletList.clear()
        }
        this.totalCount = totalCount
        mWalletList.addAll(mList)
        mWalletListAdapter.notifyDataSetChanged()
        mWalletListAdapter.loadMoreComplete()
        swipeWalletList.isRefreshing = false
        nWalletReq.pageIndex++

    }

    override fun loadWalletFail(throwable: Throwable) {
        handleError(throwable)
        swipeWalletList.isRefreshing
        mWalletListAdapter.loadMoreComplete()
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