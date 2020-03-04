package com.xhs.baselibrary.ui.update

import com.xhs.baselibrary.base.IBaseView

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/09/2019.
 * description:
 */
interface UpdateContact {
    interface UpdateView : IBaseView {
        fun updateFinish(filePath: String)
        fun updateProgress(progress: Int)
        fun onStartDownload(length: Long)
        fun updateFail(throwable: Throwable)
    }

    interface UpdatePresenter {

        fun updateApk(apkUrl: String, filePath: String)
    }
}