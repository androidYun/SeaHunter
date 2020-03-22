package com.sea.user.activity.mall.select

import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.presenter.store.NStoreListItemModel

class SelectStoreAdapter(mList: List<NStoreListItemModel>) :
    BaseQuickAdapter<NStoreListItemModel, BaseViewHolder>(
        R.layout.item_select_store_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: NStoreListItemModel) {
        helper?.setText(R.id.tvStoreName, item.title)
        helper?.setText(R.id.tvStoreAddress, item.address)
        helper?.getView<ConstraintLayout>(R.id.cvSelectStore)?.setOnClickListener {
            onItemClickListener.onItemClick(this, it, helper.layoutPosition)
        }
    }
}