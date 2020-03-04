package com.xhs.baselibrary.net.loaddown;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 05/05/2019.
 * description:
 */
public interface JsDownloadListener {
    void onStartDownload(long length);
    void onProgress(int progress);
    void onFail(String errorInfo);
}
