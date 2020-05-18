package com.sea.publicmodule.activity.search

import com.sea.publicmodule.activity.search.NAddSearchMallModelReq
import com.sea.publicmodule.activity.search.SearchItem
import com.xhs.baselibrary.base.IBaseView

interface SearchMallContact {

    interface ISearchMallView : IBaseView {

        fun clearSearch()

        fun loadAddSearch(mList: List<SearchItem>)

        fun addSearchSuccess()

        fun loadSearchStoreFail(throwable: Throwable)

    }

    interface ISearchStorePresenter {
        fun loadAddSearch(nAddSearchMallModelReq: NAddSearchMallModelReq)

        fun loadSearch()

        fun clearSearch()
    }
}