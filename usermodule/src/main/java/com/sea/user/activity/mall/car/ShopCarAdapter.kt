package com.sea.user.activity.mall.car

import android.widget.CheckBox
import android.widget.RadioButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.user.R
import com.sea.user.common.Constants
import com.sea.user.utils.sp.StoreShopSpUtils
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.xhs.baselibrary.weight.AmountView
import java.math.BigDecimal

class ShopCarAdapter(mList: List<ShopCarItem>) :
    BaseQuickAdapter<ShopCarItem, BaseViewHolder>(R.layout.item_shop_car_layout, mList) {
    override fun convert(helper: BaseViewHolder?, item: ShopCarItem) {
        ImageLoader.loadImageWithUrl(
            helper?.getView(R.id.ivCarHead),
            Constants.baseUrl.plus(item.img_url)
        )
        helper?.getView<AmountView>(R.id.AmountNumber)?.setGoods_storage(item.stock_quantity)
        helper?.getView<AmountView>(R.id.AmountNumber)?.setOnAmountChangeListener { view, amount ->
            onItemClickListener.onItemClick(this, view, helper.layoutPosition)
            mData[helper.layoutPosition].buyCount = amount
        }
        helper?.setText(R.id.tvShopName, item.title)
        helper?.setText(R.id.tvTags, item.spec_text)
        helper?.setText(R.id.tvShopPrice, "￥${item.sell_price}")
        helper?.getView<CheckBox>(R.id.cbSelect)?.isChecked = item.isCheck
        helper?.getView<CheckBox>(R.id.cbSelect)?.setOnClickListener {
            mData[helper.layoutPosition].isCheck = !mData[helper.layoutPosition].isCheck
            onItemClickListener.onItemClick(this, it, helper.layoutPosition)
            notifyItemChanged(helper.layoutPosition)
        }
    }

    fun isAllSelect(isChecked: Boolean = true) {
        mData.forEach { it.isCheck = isChecked }
        notifyDataSetChanged()
    }

    fun getSelectList(): List<NDeleteItem> {
        return mData.filter { it.isCheck }.map {
            NDeleteItem(
                article_id = it.article_id,
                channel_id = it.channel_id,
                goods_id = it.goods_id,
                shop_id = StoreShopSpUtils.getStoreShopId()
            )
        }
    }

    fun getSelectItemList(): List<ShopCarItem> {
        val toList = mData.filter { it.isCheck }.toList()
        return if (toList.isNullOrEmpty()) {
            ArrayList()
        } else {
            toList
        }
    }

    fun getAllPrice(): String {
        var allPrice = BigDecimal(0)
        mData.forEach {
            if (it.isCheck) {
                allPrice = allPrice.add(BigDecimal(it.buyCount).multiply(it.sell_price))
            }
        }
        return allPrice.setScale(2).toString()
    }

    fun getAllPoint(): Int {
        var allPoint = 0
        mData.forEach {
            if (it.isCheck) {
                allPoint += (it.point * it.buyCount)
            }
        }
        return allPoint
    }
}