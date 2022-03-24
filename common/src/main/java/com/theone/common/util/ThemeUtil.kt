package com.theone.common.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.theone.common.ext.MM_DD
import com.theone.common.ext.formatString
import java.util.*
import kotlin.collections.ArrayList

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
 * @date 2021-12-14 10:34
 * @describe 主题工具
 * @email 625805189@qq.com
 * @remark
 */
object ThemeUtil {

    private var IS_MOURNING = false
    private val DEFAULT_DATES = arrayOf("05-12", "12-13")

    fun init(application: Application, dates: Array<String> = DEFAULT_DATES) {
        val today = Date().formatString(MM_DD)
        for(date in dates){
            if (today == date) {
                IS_MOURNING = true
                break
            }
        }
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.notifyMourning()
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }

    fun Window.notifyMourning() {
        if (IS_MOURNING)
            decorView.setLayerType(View.LAYER_TYPE_HARDWARE, Paint().apply {
                colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
                    setSaturation(0.0f)
                })
            })
    }

}