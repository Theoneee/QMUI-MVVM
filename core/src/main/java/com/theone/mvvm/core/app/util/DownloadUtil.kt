package com.theone.mvvm.core.app.util

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.hjq.toast.ToastUtils
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.theone.common.callback.IImageUrl
import com.theone.mvvm.core.app.appContext
import com.theone.mvvm.core.R
import com.theone.mvvm.core.data.entity.DownloadBean
import com.theone.mvvm.core.service.startDownloadService
import com.theone.mvvm.core.app.util.imagepreview.ImageLoader
import com.theone.mvvm.core.app.util.imagepreview.ImageUtil

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
    fun startDownload(context: Activity, data: IImageUrl) {
        val mineType = when (data.resType()) {
            IImageUrl.Type.VIDEO -> SelectMimeType.SYSTEM_VIDEO
            IImageUrl.Type.IMAGE -> SelectMimeType.SYSTEM_IMAGE
            else -> SelectMimeType.SYSTEM_AUDIO
        }

        val fileName = getDownloadFileName(data.getImageUrl(), data.resType() == IImageUrl.Type.VIDEO)

        val cv = ContentValues().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(
                    MediaStore.Files.FileColumns.RELATIVE_PATH,
                    "Download/" + context.getString(R.string.app_name)
                )
            }
            put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, mineType)
        }

        val downloadPath = context.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS)

        val download = DownloadBean(
            data.getImageUrl(),
            downloadPath[0].path,
            fileName
        )
        ToastUtils.show("开始下载")
        context.startDownloadService(download)
    }

    @JvmStatic
    fun getDownloadFileName(url: String, isVideo: Boolean): String {
        return StringBuffer()
            .append("download_")
            .append(System.currentTimeMillis())
            .append(getFileSuffix(url, isVideo))
            .toString()
    }

    @JvmStatic
    fun getFileSuffix(url: String, isVideo: Boolean): String {
        var suffix = "jpg"
        val cacheFile = ImageLoader.getGlideCacheFile(appContext, url)
        if (null != cacheFile && cacheFile.exists()) {
            if (ImageUtil.isGifImageWithMime(cacheFile.absolutePath)) {
                suffix = "gif"
            }
        } else if (isVideo) {
            suffix = "mp4"
        } else if (PictureMimeType.isUrlHasImage(url)) {
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