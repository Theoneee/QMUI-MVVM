package com.theone.common.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
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
 * @date 2021-05-08 11:33
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun Context.getVersionCode(): Int {
    return packageManager.getPackageInfo(packageName, 0).versionCode
}

fun Context.getAppName(): String {
    return getString(packageManager.getPackageInfo(packageName, 0).applicationInfo.labelRes)
}

/**
 * 获取App的Icon
 *
 * @param context
 * @return
 */
fun Context.getAppIcon(): Drawable? {
    try {
        return packageManager.getApplicationIcon(packageName)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}


fun Context.installApk(file: File?) {
    file?.let {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            val uri =
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Uri.fromFile(it)
                } else {
                    // 声明需要的临时权限
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    // 第二个参数，即第一步中配置的authorities
                    FileProvider.getUriForFile(
                        this@installApk, "$packageName.fileProvider",
                        it
                    )
                }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setDataAndType(uri, "application/vnd.android.package-archive")
        })
    }

}