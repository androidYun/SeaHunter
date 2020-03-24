package com.sea.user.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sea.user.R
import com.sea.user.activity.mall.detail.Good
import com.sea.user.activity.mall.detail.NShopDetailModel
import com.sea.user.activity.mall.detail.ShopSpecItemSon
import com.sea.user.activity.mall.detail.SpecListAdapter
import com.sea.user.listener.DialogListener
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.xhs.baselibrary.weight.AmountView


class SelectShopSpecDialog(
    context: Context,
    private val nShopDetailModel: NShopDetailModel,
    private val selectShopSpecListener: DialogListener.SelectShopSpecListener
) :
    Dialog(context, R.style.Dialog_FS) {
    init {
        initView()
        initData()
        initListener()
    }


    private lateinit var specAdapter: SpecListAdapter


    private lateinit var ivImage: ImageView
    private lateinit var ivClose: ImageView
    private lateinit var tvPrice: TextView
    private lateinit var tvShopNumber: TextView
    private lateinit var rvSpec: RecyclerView
    private lateinit var tvAmount: AmountView
    private lateinit var tvConfirm: TextView
    private fun initView() {
        val inflate =
            LayoutInflater.from(context).inflate(R.layout.dialog_shop_spec_layout, null, false)
        setContentView(inflate)
        ivImage = inflate.findViewById(R.id.ivImage)
        ivClose = inflate.findViewById(R.id.ivClose)
        tvPrice = inflate.findViewById(R.id.tvPrice)
        tvShopNumber = inflate.findViewById(R.id.tvShopNumber)
        rvSpec = inflate.findViewById(R.id.rvSpec)
        tvAmount = inflate.findViewById(R.id.tvAmount)
        tvConfirm = inflate.findViewById(R.id.tvConfirm)
        val layoutParams = window?.attributes as WindowManager.LayoutParams
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
        window?.setGravity(Gravity.BOTTOM)
    }

    private fun initData() {
        ImageLoader.loadImageWithUrl(ivImage, nShopDetailModel.imageUrl)
        tvPrice.text = nShopDetailModel.sellPrice
        tvShopNumber.text = nShopDetailModel.saleNumber
        rvSpec.layoutManager = LinearLayoutManager(context)
        specAdapter = SpecListAdapter(nShopDetailModel.specs)
        rvSpec.adapter = specAdapter
        val good = getGood(specAdapter.selectSpec(), nShopDetailModel.goods)
        tvAmount.setGoods_storage(good.stock_quantity)
    }

    private var amount: Int = 1
    private fun initListener() {
        specAdapter.setOnItemClickListener { _, _, _ ->
            val good = getGood(specAdapter.selectSpec(), nShopDetailModel.goods)
            tvAmount.setGoods_storage(good.stock_quantity)
        }
        tvConfirm.setOnClickListener {
            selectShopSpecListener.selectShopSpecSuccess(amount, getGood(specAdapter.selectSpec(), nShopDetailModel.goods))
            dismiss()
        }
        tvAmount.setOnAmountChangeListener { _, amount ->
            this.amount = amount
        }
        ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun getGood(mList: List<ShopSpecItemSon>, goods: List<Good>): Good {
        var specId = ""
        mList.forEach { specId += "${it.spec_id}," }
        goods.forEach {
            if (it.spec_ids.contains(specId))
                return it
        }
        return Good()
    }
}