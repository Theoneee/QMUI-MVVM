package com.theone.mvvm.core.app.util

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.theone.common.callback.IImageUrl
import com.theone.common.util.SdkUtils
import com.theone.mvvm.core.BuildConfig
import com.theone.mvvm.core.app.appContext
import com.theone.mvvm.core.R
import com.theone.mvvm.core.data.entity.DownloadBean
import com.theone.mvvm.core.service.startDownloadService
import com.theone.mvvm.core.app.util.imagepreview.ImageLoader
import com.theone.mvvm.core.app.util.imagepreview.ImageUtil
import com.theone.mvvm.core.base.callback.CoreOnPermission

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
 * @date 2021-06-07 14:20
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object DownloadUtil {

    @JvmStatic
    fun startDownload(context: Activity, url: String,fileName: String) {
        val download = DownloadBean(
            url,
            FileDirectoryManager.getDownloadPath(),
            fileName
        )
        XXPermissions.with(context)
            .permission(if(SdkUtils.isAndroidS()) Permission.MANAGE_EXTERNAL_STORAGE else Permission.WRITE_EXTERNAL_STORAGE)
            .request(object :CoreOnPermission(context){
                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                    if(all){
                        ToastUtils.show("开始下载")
                        context.startDownloadService(download)
                    }
                }
            })

    }

    @JvmStatic
    fun startDownload(context: Activity, data: IImageUrl) {
        val fileName = getDownloadFileName(data.getImageUrl(), data.mineType())
        startDownload(context,data.getImageUrl(),fileName)
    }

    @JvmStatic
    fun getDownloadFileName(url: String, mineType: String?): String {
        return StringBuffer()
            .append("download_")
            .append(System.currentTimeMillis())
            .append(getFileSuffix(url, mineType))
            .toString()
    }

    @JvmStatic
    fun getFileSuffix(url: String,mineType: String?): String {
        var suffix = "jpg"
        val cacheFile = ImageLoader.getGlideCacheFile(appContext, url)
        if (null != cacheFile && cacheFile.exists()) {
            if (ImageUtil.isGifImageWithMime(cacheFile.absolutePath)) {
                suffix = "gif"
            }
        } else if (PictureMimeType.isUrlHasVideo(url) || PictureMimeType.isHasVideo(mineType)) {
            suffix = "mp4"
        } else if (PictureMimeType.isUrlHasImage(url) || PictureMimeType.isHasImage(mineType)) {
            try {
                val position = url.lastIndexOf(".")
                suffix = url.substring(position + 1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ".$suffix"
    }

}