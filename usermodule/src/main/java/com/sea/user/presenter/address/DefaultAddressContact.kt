package com.sea.user.presenter.address

import com.sea.user.activity.address.list.AddressListItem
import com.sea.user.activity.address.list.NAddressListModelReq
import com.xhs.baselibrary.base.IBaseView

interface DefaultAddressContact {

    interface IDefaultAddressView : IBaseView {

        fun loadDefaultAddressSuccess(addressListItem:AddressListItem)

        fun loadDefaultAddressFail(throwable: Throwable)

    }

    interface IDefaultAddressPresenter {
        fun loadDefaultAddress(nAddressListModelReq: NAddressListModelReq)
    }
}