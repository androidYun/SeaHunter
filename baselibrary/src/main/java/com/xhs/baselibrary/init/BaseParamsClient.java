package com.xhs.baselibrary.init;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/05/2019.
 * description:
 */
public class BaseParamsClient {
    private static BaseParamsClient sInstance;

    public static BaseParamsClient getInstance() {
        if (sInstance == null) {
            sInstance = new BaseParamsClient();
        }
        return sInstance;
    }

    BaseParamsProvide baseParamsProvide;

    public void init(BaseParamsProvide baseParamsProvide) {
        this.baseParamsProvide = baseParamsProvide;
    }


    public String getBaseUrl() {
        if (baseParamsProvide != null) {
            return baseParamsProvide.getBaseUrl();
        }
        return "";
    }

    public String getToken() {
        if (baseParamsProvide != null) {
            return baseParamsProvide.getToken();
        }
        return "";
    }

    public int getUserId() {
        if (baseParamsProvide != null) {
            return baseParamsProvide.getUserId();
        }
        return -1;
    }
}
