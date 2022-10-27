package com.theone.mvvm.core.app.util

import com.theone.common.util.FileUtils
import com.theone.mvvm.core.app.appContext
import com.theone.mvvm.core.R
import java.io.File

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * @author The one
 * @date 2021-07-12 10:38
 * @describe 文件目录
 * @email 625805189@qq.com
 * @remark
 */
object FileDirectoryManager {

    /**
     * 根目录
     */
    var INDEX = appContext.getString(R.string.app_name)

    /**
     * 下载目录
     */
    var DOWNLOAD = "Download"

    /**
     * 图片目录
     */
    var PICTURE = "Picture"

    /**
     * 视频目录
     */
    var VIDEO = "Video"

    /**
     * 临时文件夹
     */
    var TEMP = "Temp"

    /**
     * 缓存目录
     */
    var CACHE = "Cache"

    /**
     * 压缩目录
     */
    var COMPRESS = "Compress"

    /**
     * 更新包下载文件目录
     */
    var UPDATE_APK_FILE_NAME = "APK"

    /**
     * 补丁目录
     */
    var PATCH = "Patch"

    /**
     * 补丁名称
     */
    var PATCH_NAME = "patch"

    /**
     * 获取根目录
     * @return String
     */
    fun getIndexPath(): String = createExternalFileDir()

    /**
     * 获取缓存目录
     * @return String
     */
    fun getCachePath(): String = createExternalFileDir(CACHE)

    /**
     * 获取缓存子文件目录
     * @param childFileName String
     * @return String
     */
    fun getCacheChildFilePath(childFileName: String): String =
        createCacheDir(CACHE + File.separator + childFileName)

    /**
     * 获取下载目录
     * @return String
     */
    fun getDownloadPath(): String = createExternalFileDir(DOWNLOAD)

    /**
     * 图片目录
     * @return String
     */
    fun getPicturePath(): String = createCacheDir(PICTURE)

    /**
     * 视频目录
     * @return String
     */
    fun getVideoPath(): String = createCacheDir(VIDEO)

    /**
     * 临时文件目录
     * @return String
     */
    fun getTempPath(): String = createCacheDir(TEMP)

    /**
     * 补丁文件目录
     * @return String
     */
    fun getPatchPath(): String = createCacheDir(PATCH)

    /**
     * APK更新文件下载目录
     * @return String
     */
    fun getUpdateAPKDownloadPath(): String =
        createCacheDir(DOWNLOAD + File.separator + UPDATE_APK_FILE_NAME)

    /**
     * 压缩目录
     * @return String
     */
    fun getCompressPath(): String = getCacheChildFilePath(COMPRESS)

    fun createCacheDir(name: String = ""): String =
        FileUtils.createFileDir(appContext, name,false).path

    /**
     * 创建外部存储文件夹
     * @param name String
     * @return (File..File?)
     */
    fun createExternalFileDir(name: String = ""):String = FileUtils.createFileDir(appContext, INDEX+File.separator+name,true).path

}