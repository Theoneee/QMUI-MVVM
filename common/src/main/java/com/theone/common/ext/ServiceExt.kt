package com.theone.common.ext

import android.app.ActivityManager
import android.content.Context

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
 * @date 2021-05-08 11:47
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun isServiceExisted(context: Context,className:String):Boolean{
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val serviceList = activityManager.getRunningServices(Integer.MAX_VALUE)
    if(serviceList.isEmpty()) return false
    for (serviceInfo in serviceList){
        val serviceName = serviceInfo.service
        if(serviceName.className == className){
            return true
        }
    }
    return false
}