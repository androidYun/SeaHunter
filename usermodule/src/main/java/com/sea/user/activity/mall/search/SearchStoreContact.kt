package com.sea.user.activity.mall.search

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface SearchStoreContact {

    interface ISearchStoreView : IBaseView {

        fun loadHotSearchSuccess(mList: List<String>)

        fun clearSearch()

        fun loadAddSearch()

        fun loadSearchStoreFail(throwable: Throwable)

    }

    interface ISearchStorePresenter {
        fun loadAddSearch(nSearchStoreModelReq: NSearchStoreModelReq)

        fun loadHotSearch()

        fun clearHotSearch()
    }
}