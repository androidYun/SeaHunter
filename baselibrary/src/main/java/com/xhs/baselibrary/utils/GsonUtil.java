package com.xhs.baselibrary.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 02/04/2019.
 * description:
 */
public class GsonUtil {
    private static GsonUtil sInstance;

    public static GsonUtil getInstance() {
        if (sInstance == null) {
            sInstance = new GsonUtil();
        }
        return sInstance;
    }

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public  String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public  <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public  <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

}
