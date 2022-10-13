package com.theone.mvvm.core.app

import android.app.Application
import com.hjq.toast.ToastUtils
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.theone.common.util.AppViewModelManager
import com.theone.common.ext.LogInit
import com.theone.mvvm.core.app.ext.initLoaderDefault
import com.theone.mvvm.core.app.util.MMKVUtil
import com.theone.mvvm.core.app.util.NotificationManager
import kotlin.properties.Delegates

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
 * @date 2021/3/23 0022
 * @describe 对Core里面的组件进行初始化
 * @email 625805189@qq.com
 * @remark
 */

val appContext: CoreApplication by lazy { CoreApplication.app }

abstract class CoreApplication : Application() {

    companion object {
        lateinit var app: CoreApplication
        var DEBUG by Delegates.notNull<Boolean>()
    }

    abstract fun isDebug(): Boolean

    override fun onCreate() {
        super.onCreate()
        app = this
        DEBUG = isDebug()
        init(this)
    }

    protected open fun init(application: Application) {
        AppViewModelManager.init(this)
        QMUISwipeBackActivityManager.init(this)
        MMKVUtil.init(application)
        LogInit(DEBUG)
        NotificationManager.getInstance().register()
        ToastUtils.init(this)
        initLoaderDefault()
    }

}