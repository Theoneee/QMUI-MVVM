package com.theone.mvvm.core.app.util

import android.graphics.Bitmap
import com.theone.common.util.FileUtils
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
 * @date 2021-07-12 10:47
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object FileManager {

    fun saveBitmap(
        bitmap: Bitmap?,
        path: String = FileDirectoryManager.getPicturePath(),
        fileName: String = "picture_${System.currentTimeMillis()}.jpg",
        quality: Int = 100
    ) = FileUtils.saveBitmap(path, fileName, bitmap, quality)

    fun getCacheFileSize(): String =
        FileUtils.formatFileSize(FileUtils.getFileSize(File(FileDirectoryManager.getCachePath())))

    fun deleteCacheFile(): Boolean = FileUtils.deleteDir(File(FileDirectoryManager.getCachePath()))

}