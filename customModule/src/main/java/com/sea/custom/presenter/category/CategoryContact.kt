package com.sea.custom.presenter.category

import com.xhs.baselibrary.base.IBaseView
import com.sea.custom.R

interface CategoryContact {

    interface ICategoryView : IBaseView {

        fun loadCategorySuccess(mCategoryList: List<NCategoryItem>)

        fun loadCategoryFail(throwable: Throwable)

    }

    interface ICategoryPresenter {
        fun loadCategory(nCategoryModelReq: NCategoryModelReq)
    }
}