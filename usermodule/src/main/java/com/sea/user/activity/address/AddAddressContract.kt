package com.sea.user.activity.address

import com.xhs.baselibrary.base.IBaseView

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
interface AddAddressContract {

    interface IAddAddressView : IBaseView {

        fun loadAddAddressSuccess()

        fun loadAddAddressFail(throwable: Throwable)
    }

    interface IAddAddressPresenter {
        fun loadAddAddress( nAddressModelReq: NAddressModelReq)
    }
}