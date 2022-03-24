package com.theone.mvvm.core.app.ext

import android.app.Activity
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.theone.mvvm.core.ui.activity.TheErrorActivity

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
 * @date 2021-12-17 10:02
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

/**
 * 需要异常捕获，在自己的Application里进行初始化
 * @param launcherActivity Class<out Activity> 重启后的Activity
 * @param errorActivity Class<out Activity> 崩溃后跳转到的Activity
 */
fun initCrashConfig(
    launcherActivity: Class<out Activity>,
    errorActivity: Class<out Activity> = TheErrorActivity::class.java
) {
    //防止项目崩溃，崩溃后打开错误界面
    CaocConfig.Builder.create()
        .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
        .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
        .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
        .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
        .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
        .restartActivity(launcherActivity) // 重启的activity
        .errorActivity(errorActivity)
        .apply()
}