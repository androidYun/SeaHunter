package com.xhs.baselibrary.utils.imageLoader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.xhs.baselibrary.utils.CacheUtil;

/**
 * Created by zhf on 2018/10/12 0012.
 */

@GlideModule
public class GlideModuleConfig extends AppGlideModule{

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);

        MemorySizeCalculator memorySizeCalculator=new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize=memorySizeCalculator.getMemoryCacheSize();
        int defaultBitmapPoolSize=memorySizeCalculator.getBitmapPoolSize();
        int maxMemory= (int) Runtime.getRuntime().maxMemory();
        int memoryCacheSize=maxMemory/8;

        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));


        int diskCacheSize=1024*1024*300;
        builder.setDiskCache(new DiskLruCacheFactory(CacheUtil.imagePath,"glide",diskCacheSize));

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
