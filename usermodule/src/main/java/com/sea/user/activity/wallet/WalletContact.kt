package com.sea.user.activity.wallet

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface WalletContact {

    interface IWalletView : IBaseView {

        fun loadWalletSuccess(content: Any)

        fun loadWalletFail(throwable: Throwable)

    }

    interface IWalletPresenter {
        fun loadWallet(nWalletModelReq: NWalletModelReq)
    }
}