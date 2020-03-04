package com.xhs.baselibrary.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xhs.baselibrary.BaseApplication;

import java.util.Map;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 18/04/2019.
 * description:
 */
public class BaseSharePreUtils {

    private String sPrefFileName;

    public BaseSharePreUtils(String sPrefFileName) {
        this.sPrefFileName = sPrefFileName;
        checkInit();
    }

    private void checkInit() {
        if (TextUtils.isEmpty(sPrefFileName)) {
            throw new RuntimeException("Call init before any other method!");
        }
    }

    private SharedPreferences getPreference() {
        return BaseApplication.getsInstance().getSharedPreferences(sPrefFileName, Context.MODE_PRIVATE);
    }


    public void clear() {
        getPreference().edit().clear().commit();
    }


    public boolean contains(String key) {
        return getPreference() != null && getPreference().contains(key);
    }

    public float getFloat(String key, float defValue) {
        return getPreference() != null ? getPreference().getFloat(key, defValue) : defValue;
    }

    public int getInt(String key, int defValue) {
        return getPreference() != null ? getPreference().getInt(key, defValue) : defValue;
    }

    public long getLong(String key, long defValue) {
        return getPreference() != null ? getPreference().getLong(key, defValue) : defValue;
    }

    public String getString(String key) {
        return getPreference() != null ? getPreference().getString(key, "") : "";
    }

    public String getString(String key, String defValue) {
        return getPreference() != null ? getPreference().getString(key, defValue) : defValue;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getPreference() != null ? getPreference().getBoolean(key, defValue) : defValue;
    }

    public Map<String, ?> getAll() {
        return getPreference() != null ? getPreference().getAll() : null;
    }


    public void putFloat(String key, float value) {
        getPreference().edit().putFloat(key, value).commit();
    }

    public void putInt(String key, int value) {
        getPreference().edit().putInt(key, value).commit();
        getPreference().edit().commit();
    }

    public void putLong(String key, long value) {
        getPreference().edit().putLong(key, value).commit();
    }

    public void putString(String key, String value) {
        getPreference().edit().putString(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        getPreference().edit().putBoolean(key, value).commit();
    }

    public void remove(String key) {
        getPreference().edit().remove(key).commit();
    }

}
