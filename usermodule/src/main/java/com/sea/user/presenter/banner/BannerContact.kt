package com.sea.user.presenter.banner

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface BannerContact {

    interface IBannerView : IBaseView {

        fun loadBannerSuccess(`data`: List<BannerItem>)

        fun loadBannerFail(throwable: Throwable)

    }

    interface IBannerPresenter {
        fun loadBanner()
    }
}