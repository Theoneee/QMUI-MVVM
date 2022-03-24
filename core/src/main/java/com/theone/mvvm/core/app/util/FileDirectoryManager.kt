package com.theone.mvvm.core.app.util

import com.theone.common.util.FileUtils
import com.theone.mvvm.base.appContext
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
    fun getIndexPath(): String = createDir()

    /**
     * 获取缓存目录
     * @return String
     */
    fun getCachePath(): String = createDir(CACHE)

    /**
     * 获取缓存子文件目录
     * @param childFileName String
     * @return String
     */
    fun getCacheChildFilePath(childFileName: String): String =
        createDir(CACHE + File.separator + childFileName)

    /**
     * 获取下载目录
     * @return String
     */
    fun getDownloadPath(): String = createDir(DOWNLOAD)

    /**
     * 图片目录
     * @return String
     */
    fun getPicturePath(): String = createDir(PICTURE)

    /**
     * 视频目录
     * @return String
     */
    fun getVideoPath(): String = createDir(VIDEO)

    /**
     * 临时文件目录
     * @return String
     */
    fun getTempPath(): String = createDir(TEMP)

    /**
     * 补丁文件目录
     * @return String
     */
    fun getPatchPath(): String = createDir(PATCH)

    /**
     * APK更新文件下载目录
     * @return String
     */
    fun getUpdateAPKDownloadPath(): String =
        createDir(DOWNLOAD + File.separator + UPDATE_APK_FILE_NAME)

    /**
     * 压缩目录
     * @return String
     */
    fun getCompressPath(): String = getCacheChildFilePath(COMPRESS)

    private fun createDir(name: String = ""): String =
        FileUtils.createFileDir(
            appContext, INDEX + (if (name.isEmpty()) "" else File.separator + name)
        ).path


}