package com.sea.user.presenter.update

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface UpdateImageContact {

    interface IUpdateImageView : IBaseView {

        fun loadUpdateImageSuccess(imageUrl: String)

        fun loadUpdateImageFail(throwable: Throwable)

    }

    interface IUpdateImagePresenter {
        fun loadUpdateImage(type: String, imagePath: String)
    }
}