package com.sea.custom.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.xhs.baselibrary.weight.ShapedImageView
import com.youth.banner.adapter.BannerAdapter


class ShopBannerAdapter(mList: List<String>, private val isFullScreen: Boolean = false) :
    BannerAdapter<String, ShopBannerAdapter.BannerViewHolder>(mList) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            if (isFullScreen) {
                LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_full_screen_banner_layout, parent, false)
            } else {
                LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_banner_layout, parent, false)
            }
        )
    }

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
        ImageLoader.loadImageWithUrl(
            holder?.imageView,
            Constants.baseUrl.plus(data)
        )
    }

    class BannerViewHolder(
        @NonNull
        view: View
    ) :
        RecyclerView.ViewHolder(view) {
        private var inflaterView: View = view
        var imageView: ShapedImageView

        init {
            imageView = inflaterView.findViewById(R.id.ivBannerView)
        }
    }
}