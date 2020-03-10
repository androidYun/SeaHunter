package com.sea.user.activity.mall.search

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface SearchStoreContact {

    interface ISearchStoreView : IBaseView {

        fun loadSearchStoreSuccess(mList: List<SearchStoreItem>, totalCount: Int)

        fun loadSearchStoreFail(throwable: Throwable)

    }

    interface ISearchStorePresenter {
        fun loadSearchStore(nSearchStoreModelReq: NSearchStoreModelReq)
    }
}