package com.sea.user.activity.mall.detail

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sea.user.R

class SpecListAdapter(mList: List<ShopSpecItem>) :
    BaseQuickAdapter<ShopSpecItem, BaseViewHolder>(R.layout.item_spec_list_layout, mList) {

    override fun convert(helper: BaseViewHolder?, item: ShopSpecItem) {
        helper?.setText(R.id.tvSpecTitle, item.title)
        val rvSpecList = helper?.getView<RecyclerView>(R.id.rvSpecList)
        val layoutManager = FlexboxLayoutManager(mContext)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rvSpecList?.layoutManager = layoutManager
        val specAdapter = SelectSpcAdapter(item.son)
        rvSpecList?.adapter = specAdapter
    }

    fun selectSpec(): List<ShopSpecItemSon> {
        val sonList = mutableListOf<ShopSpecItemSon>()
        mData.forEach {
            val filter = it.son.filter { son -> son.isSelect }
            sonList.addAll(filter)
        }
        return sonList
    }
}