package com.theone.mvvm.core.app.util;

import android.os.Environment;

import com.theone.mvvm.base.BaseApplication;
import com.theone.mvvm.core.R;

import java.io.File;

/**
 * @author The one
 * @date 2018/9/20 0020
 * @describe 文件目录
 * @email 625805189@qq.com
 * @remark
 */
public class FileDirectoryUtil {

    /**
     * 根目录
     */
    private static  String INDEX = BaseApplication.app.getString(R.string.app_name);
    /**
     * 下载目录
     */
    private static  String DOWNLOAD = "Download";
    /**
     * 图片目录
     */
    private static  String PICTURE = "Picture";
    /**
     * 视频目录
     */
    private static  String VIDEO = "Video";
    /**
     * 缓存目录
     */
    private static  String CACHE = "Cache";

    /**
     * 压缩目录
     */
    private static  String IMAGE_COMPRESS = "ImageCompress";
    /**
     * 更新包下载地址
     */
    private static  String UPDATE_APK_FILE_NAME = "APK";

    /**
     * 省区县json
     */
    public static String PROVINCE_JSON = "province.json";

    /**
     * 补丁
     */
    private static String PATCH = "Patch";

    /**
     * 补丁名称
     */
    public static String PATCH_NAME = "patch";

    private static Builder mBuilder;

    public static Builder getBuilder(){
        if(null == mBuilder){
            mBuilder = new Builder();
        }
        return mBuilder;
    }

    /**
     * 获取根目录
     * @return
     */
    public static String getIndexPath (){
        return checkFileExists(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +INDEX);
    }

    /**
     * 获取下载目录
     * @return
     */
    public static String getDownloadPath(){
        return checkFileExists(getIndexPath() + File.separator + DOWNLOAD );
    }

    /**
     * 获取更新包路径
     * @return
     */
    public static String getUpdateAPKDownloadPath(){
        return getDownloadPath() + File.separator + UPDATE_APK_FILE_NAME;
    }

    /**
     * 获取补丁文件路径
     * @return
     */
    public static String getPatchFilePath(){
        return getPatchPath()+ File.separator + PATCH_NAME;
    }

    /**
     * 获取补丁路径
     * @return
     */
    public static String getPatchPath(){
        return getPath(PATCH);
    }

    /**
     * 获取图片目录
     * @return
     */
    public static String getPicturePath(){
        return getPath( PICTURE );
    }

    /**
     * 获取视频目录
     * @return
     */
    public static String getVideoPath(){
        return getPath( VIDEO );
    }


    /**
     * 获取缓存目录
     * @return
     */
    public static String getCachePath(){
        return getPath( CACHE );
    }

    /**
     * 获取缓存子文件目录
     * @return
     */
    public static String getCacheChildFilePath(String name){
        return checkFileExists(getCachePath() + File.separator+ name);
    }


    /**
     * 获取省市区的json文件地址
     * @return
     */
    public static String getProvinceJsonPath(){
        return getCacheChildFilePath(PROVINCE_JSON);
    }

    /**
     * 图片压缩目录
     * @return
     */
    public static String getImageCompressPath(){
        return getCacheChildFilePath(IMAGE_COMPRESS);
    }

    private static String getPath(String path){
        return checkFileExists(getIndexPath() + File.separator + path );
    }

    private static String checkFileExists(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path;
    }

    public static class Builder{

        /**
         * 根目录
         */
        public String mIndex = "The one";
        /**
         * 下载目录
         */
        public String mDownload = "Download";
        /**
         * 图片目录
         */
        public String mPicture = "Picture";
        /**
         * 视频目录
         */
        public String mVideo = "Video";
        /**
         * 缓存目录
         */
        public String mCache = "Cache";
        /**
         * 补丁目录
         */
        public String mPatch = "Patch";
        /**
         * 更新包下载路径名称
         */
        public String mUpdateApkFileName = "Apk";

        public String getIndex() {
            return mIndex;
        }

        public Builder setIndex(String mIndex) {
            this.mIndex = mIndex;
            return this;
        }

        public String getDownload() {
            return mDownload;
        }

        public Builder setDownload(String mDownload) {
            this.mDownload = mDownload;
            return this;
        }

        public String getPicture() {
            return mPicture;
        }

        public Builder setPicture(String mPicture) {
            this.mPicture = mPicture;
            return this;
        }

        public String getVideo() {
            return mVideo;
        }

        public Builder setVideo(String mVideo) {
            this.mVideo = mVideo;
            return this;
        }

        public String getPatch() {
            return mPatch;
        }

        public void setPatch(String patch) {
            this.mPatch = patch;
        }

        public String getCache() {
            return mCache;
        }

        public Builder setCache(String mCache) {
            this.mCache = mCache;
            return this;
        }

        public String getUpdateApkFileName() {
            return mUpdateApkFileName;
        }

        public Builder setUpdateApkFileName(String mUpdateApkName) {
            this.mUpdateApkFileName = mUpdateApkName;
            return this;
        }

        public void build(){
            FileDirectoryUtil.INDEX = getIndex();
            FileDirectoryUtil.DOWNLOAD = getDownload();
            FileDirectoryUtil.PICTURE = getPicture();
            FileDirectoryUtil.VIDEO = getVideo();
            FileDirectoryUtil.CACHE = getCache();
            FileDirectoryUtil.PATCH = getPatch();
            FileDirectoryUtil.UPDATE_APK_FILE_NAME = getUpdateApkFileName();
        }
    }

}
