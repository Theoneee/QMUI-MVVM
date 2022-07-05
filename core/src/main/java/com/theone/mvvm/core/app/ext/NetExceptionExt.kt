package com.theone.mvvm.core.app.ext

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.JsonSyntaxException
import com.theone.mvvm.base.appContext
import kotlinx.coroutines.TimeoutCancellationException
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

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
 * @date 2021-03-29 09:59
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

val Throwable.code: Int
    get() {
        val errorCode = when (this) {
            is HttpStatusCodeException -> this.statusCode // Http状态码异常
            is ParseException -> this.errorCode     // 业务code异常
            else -> "-1"
        }
        return try {
            errorCode.toString().toInt()
        } catch (e: Exception) {
            -1
        }
    }

val Throwable.msg: String
    get() {
        val customResult = customParseException?.invoke(this)
        if(!customResult.isNullOrEmpty()){
            return customResult
        }
        return if (this is UnknownHostException) {
            //网络异常
            "当前无网络，请检查你的网络设置"
        } else if (
            this is SocketTimeoutException  //okHttp全局设置超时
            || this is TimeoutException     //方法超时
            || this is TimeoutCancellationException  //协程超时
        ) {
            "连接超时,请稍后再试"
        } else if (this is ConnectException) {
            "网络不给力，请稍候重试！"
        } else if (this is HttpStatusCodeException) {
            if (code == 502) "系统正在升级," else "Http状态码异常"
        } else if (this is JsonSyntaxException) {
            //请求成功，但Json语法异常,导致解析失败
            "数据解析失败,请检查数据是否正确"
        } else if (this is ParseException) {
            // ParseException异常表明请求成功，但是数据不正确 msg为空，显示code
            this.message ?: errorCode
        } else {
            "请求失败，请稍后再试"
        }
    }

internal var customParseException: ((Throwable) -> String?)? = null

fun isNetworkConnected(): Boolean {
    val manager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetworkInfo
    networkInfo?.let {
        return it.isAvailable
    }
    return false
}