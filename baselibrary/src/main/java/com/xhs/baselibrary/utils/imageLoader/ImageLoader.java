package com.xhs.baselibrary.utils.imageLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xhs.baselibrary.BaseApplication;
import com.xhs.baselibrary.R;


/**
 * Created by zhf on 2018/10/12 0012.
 */

public class ImageLoader {

    public static void loadImageWithUrl(ImageView view, String url, int defaultRes) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(defaultRes)
                .error(defaultRes)
                .centerCrop()
                .into(view);
    }

    public static void loadImageWithUrl(ImageView view, int redId) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(redId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(redId)
                .error(redId)
                .centerCrop()
                .into(view);
    }

    public static void loadImageWithUrl(ImageView view, int redId, int defaultRes) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(redId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(defaultRes)
                .error(defaultRes)
                .centerCrop()
                .into(view);
    }

    public static void loadImageWithUrl(ImageView view, String url) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.color.base_color6)
                .centerCrop()
                .into(view);
    }

    public static void loadCircleImageView(ImageView view, String url) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(url)
                .transform(new GlideCircleTransform())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.mipmap.ic_head_default)
                .error(R.mipmap.ic_head_default)
                .centerCrop()
                .into(view);
    }

    public static void loadAvatarWithUrl(ImageView view, String url) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.mipmap.ic_head_default)
                .error(R.mipmap.ic_head_default)
                .centerCrop()
                .into(view);
    }

    public static void loadImageViewWithPathResize(ImageView view, String path, int width, int height) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(path)
                .placeholder(R.color.base_color_efefef)
                .override(width, height)
                .centerCrop()
                .into(view);
    }

    public static void loadImageWithUrlResize(ImageView view, String url, int width, int height) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.color.base_color_accent)
                .override(width, height)
                .centerCrop()
                .into(view);
    }

    public static void loadImageRoundedCorners(ImageView view, String url, int radius) {
        GlideApp.with(view.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .transform(new GlideRoundTransform(radius)))
                .placeholder(R.color.base_color_efefef)
                .error(R.color.base_color_efefef)
                .centerCrop()
                .into(view);
    }

    public static void loadImageWithPath(ImageView view, String path) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.mipmap.ic_image_loading)
                .error(R.mipmap.ic_image_expire)
                .centerCrop()
                .into(view);
    }

    public static void loadImageForVideo(ImageView view, String path) {
        Glide.with(view.getContext()).load(path).into(view);
    }
}
