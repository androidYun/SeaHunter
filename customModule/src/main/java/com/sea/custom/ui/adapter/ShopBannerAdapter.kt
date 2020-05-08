package com.sea.custom.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sea.custom.common.Constants
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.youth.banner.adapter.BannerAdapter


class ShopBannerAdapter(mList:List<String>) : BannerAdapter<String, ShopBannerAdapter.BannerViewHolder>(mList) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
        ImageLoader.loadImageWithUrl(
            holder?.imageView,
            Constants.baseUrl.plus(data)
        )
    }

    class BannerViewHolder(
        @NonNull
        view: ImageView
    ) :
        RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        init {
            imageView = view
        }
    }
}