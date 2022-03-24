package com.theone.mvvm.core.app.util

import android.app.Activity
import com.hjq.toast.ToastUtils
import com.luck.picture.lib.config.PictureMimeType
import com.theone.common.callback.IImageUrl
import com.theone.mvvm.base.appContext
import com.theone.mvvm.core.data.entity.DownloadBean
import com.theone.mvvm.core.service.startDownloadService
import com.theone.mvvm.core.app.util.imagepreview.ImageLoader
import com.theone.mvvm.core.app.util.imagepreview.ImageUtil
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
 * @date 2021-06-07 14:20
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object DownloadUtil {

    @JvmStatic
    fun startDownload(context: Activity, data: IImageUrl) {
        val type = if (data.isVideo()) "Video" else "Picture"
        val download = DownloadBean(
            data.getImageUrl(),
            FileDirectoryManager.getDownloadPath() + File.separator + type,
            getDownloadFileName(data.getImageUrl(),data.isVideo())
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
        } else if (PictureMimeType.isSuffixOfImage(url)) {
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