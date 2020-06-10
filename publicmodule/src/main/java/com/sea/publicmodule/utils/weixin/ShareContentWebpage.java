package com.sea.publicmodule.utils.weixin;


/**
 * 设置分享链接的内容
 *
 * @author Administrator
 */
public class ShareContentWebpage extends ShareContent {
    private String title;
    private String content;
    private String url;
    private int picResource;

    public ShareContentWebpage(String title, String content,
                               String url, int picResource) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.picResource = picResource;
    }

    @Override
    protected String getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected String getURL() {
        return url;
    }

    @Override
    protected int getPicResource() {
        return picResource;
    }

    @Override
    protected int getShareWay() {
        return WeixinShareManager.WEIXIN_SHARE_WAY_WEBPAGE;
    }

}

