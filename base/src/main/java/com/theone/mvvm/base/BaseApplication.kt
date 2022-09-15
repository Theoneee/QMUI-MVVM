package com.theone.mvvm.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
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
 * @date 2021/2/22 0022
 * @describe 提供一个功能--在Activity/fragment中获取Application级别的ViewModel
 * @email 625805189@qq.com
 * @remark
 */

val appContext: Application by lazy { BaseApplication.app }

abstract class BaseApplication : Application(), AppViewModelProviderOwner {

    companion object{
        lateinit var app: Application
        var DEBUG by Delegates.notNull<Boolean>()
    }

    abstract fun isDebug():Boolean

    private val mAppViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    private val mFactory: ViewModelProvider.Factory by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        DEBUG = isDebug()
        QMUISwipeBackActivityManager.init(this)
        init(this)
    }

    abstract fun init(application: Application)

    /**
     * 获取一个Application级别的ViewModelProvider
     */
    override fun getAppViewModelProvider(): ViewModelProvider = ViewModelProvider(mAppViewModelStore,mFactory)

}