package com.xhs.baselibrary.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * 缓存工具类
 * Created by zhf on 2018/12/17.
 */

public class CacheUtil {
    public static final String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator+ "Caches"+File.separator;

    public static void init() {
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 删除指定文件夹下的所有文件
     * @param path
     * @return
     */
    public static boolean delAllFile(String path){
        boolean flag=false;
        File file=new File(path);
        if(!file.exists()){
            return flag;
        }
        if(!file.isDirectory()){
            return flag;
        }
        String[] tempList=file.list();
        File temp=null;
        for(int i=0;i<tempList.length;i++){
            if(path.endsWith(File.separator)){
                temp=new File(path+tempList[i]);
            }else{
                temp=new File(path+File.separator+tempList[i]);
            }
            if(temp.isFile()){
                temp.delete();
            }
            if(temp.isDirectory()){
                delAllFile(path+"/"+tempList[i]);
                flag=true;
            }
        }
        return flag;
    }

    /**
     * 获取缓存大小
     * @param filePath
     * @return
     */
    public static String getCacheSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
                blockSize=getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatSize(blockSize);
    }

    /**
     * 获取文件夹
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File file) throws Exception {
        long size = 0;
        File fileList[] = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFileSizes(fileList[i]);
            } else {
                size = size + getFileSize(fileList[i]);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    private static String formatSize(long size){
        DecimalFormat decimalFormat=new DecimalFormat("####.00");
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            float kbSize = size / 1024f;
            return decimalFormat.format(kbSize) + " KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbSize = size / 1024f / 1024f;
            return decimalFormat.format(mbSize) + " M";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbSize = size / 1024f / 1024f / 1024f;
            return decimalFormat.format(gbSize) + " G";
        } else {
            return "size: error";
        }
    }
}
