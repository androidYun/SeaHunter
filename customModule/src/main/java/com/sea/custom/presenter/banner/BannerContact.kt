package com.sea.custom.presenter.banner

import com.xhs.baselibrary.base.IBaseView

interface BannerContact {

    interface IBannerView : IBaseView {

        fun loadBannerSuccess(`data`: List<BannerItem>)

        fun loadBannerFail(throwable: Throwable)

    }

    interface IBannerPresenter {
        fun loadBanner()
    }
}