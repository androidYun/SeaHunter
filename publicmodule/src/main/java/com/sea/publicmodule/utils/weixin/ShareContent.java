package com.sea.publicmodule.utils.weixin;

public abstract class ShareContent {
    protected abstract int getShareWay();

    protected abstract String getContent();

    protected abstract String getTitle();

    protected abstract String getURL();

    protected abstract int getPicResource();
}
