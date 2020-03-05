package com.sea.user.activity.address.list

import com.xhs.baselibrary.base.IBaseView
import com.sea.user.R

interface AddressListContact {

    interface IAddressListView : IBaseView {

        fun loadAddressListSuccess(mList: List<AddressListItem>)

        fun loadAddressListFail(throwable: Throwable)

    }

    interface IAddressListPresenter {
        fun loadAddressList(nAddressListModelReq: NAddressListModelReq)
    }
}