package com.sea.publicmodule.utils.weixin;

/**
 * 设置分享图片的内容
 *
 * @author Administrator
 */
public class ShareContentPic extends ShareContent {
    private int picResource;

    public ShareContentPic(int picResource) {
        this.picResource = picResource;
    }

    @Override
    protected String getContent() {
        return null;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected String getURL() {
        return null;
    }

    @Override
    protected int getPicResource() {
        return picResource;
    }

    @Override
    protected int getShareWay() {
        return WeixinShareManager.WEIXIN_SHARE_WAY_PIC;
    }
}