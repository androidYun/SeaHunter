package com.sea.publicmodule.utils.weixin;


/**
 * 设置分享文字的内容
 *
 * @author Administrator
 */
public class ShareContentText extends ShareContent {
    private String content;

    /**
     * 构造分享文字类
     *
     * @param content 分享的文字内容
     */
    public ShareContentText(String content) {
        this.content = content;
    }

    @Override
    protected String getContent() {

        return content;
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
        return -1;
    }

    @Override
    protected int getShareWay() {
        return WeixinShareManager.WEIXIN_SHARE_WAY_TEXT;
    }

}