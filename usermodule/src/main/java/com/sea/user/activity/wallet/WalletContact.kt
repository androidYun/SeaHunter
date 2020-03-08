package com.sea.user.activity.wallet

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface WalletContact {

    interface IWalletView : IBaseView {

        fun loadWalletSuccess(mList: List<WalletListItem>, totalCount: Int)

        fun loadWalletFail(throwable: Throwable)

    }

    interface IWalletPresenter {
        fun loadWallet(nWalletModelReq: NWalletModelReq)
    }
}