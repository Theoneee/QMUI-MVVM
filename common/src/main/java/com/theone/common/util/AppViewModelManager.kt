package com.theone.common.util

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.theone.common.callback.AppViewModelProviderOwner

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
 * @date 2022-10-13 08:46
 * @describe Application级别ViewModel提供者
 * @email 625805189@qq.com
 * @remark
 */
class AppViewModelManager : AppViewModelProviderOwner {

    companion object {

        private var mAppViewModelProvider: ViewModelProvider? = null

        var INSTANCE: AppViewModelProviderOwner? = null
            get() {
                if (field == null) {
                    throw RuntimeException("请在Application中初始化： ${AppViewModelManager::class.java.simpleName}.init(Application)")
                }
                return field
            }

        fun init(application: Application) {
            mAppViewModelProvider = ViewModelProvider(
                ViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory(application)
            )
            INSTANCE = AppViewModelManager()
        }

    }

    override fun getAppViewModelProvider(): ViewModelProvider = mAppViewModelProvider!!

}