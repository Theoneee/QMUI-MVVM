package com.theone.common.util

import android.os.Build

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
 * @date 2021-08-17 15:06
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

object SdkUtils{

    fun isAndroidS() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    fun isAndroidR() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    fun isAndroidQ() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    fun isAndroidP() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    fun isAndroidO() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

}