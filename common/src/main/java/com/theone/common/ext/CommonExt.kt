package com.theone.common.ext

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout

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
 * @date 2021-03-29 14:26
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}

/**
 * 延迟任务
 * @param time Long 延迟时间
 * @param action Function0<Unit>
 */
fun delay(time: Long, action: () -> Unit) {
    // 获取当前线程Looper，如果是主线程肯定不会为空
    val looper = Looper.myLooper()
    // 如果是主线程直接使用主线程的Looper
    if (looper == Looper.getMainLooper()) {
        Handler(Looper.getMainLooper()).postDelayed(action, time)
        return
    }
    // 如果没有开启Looper，需要这里处理开启和循环和退出
    val noPrepare = null == looper
    if (noPrepare) {
        Looper.prepare()
    }
    // 这里的Looper就一定不为空了
    Looper.myLooper()?.let {
        Handler(it).postDelayed({
            action.invoke()
            if (noPrepare){
                it.quitSafely()
            }
        }, time)
    }
    // 开起消息循环
    if (noPrepare)
        Looper.loop()
}
