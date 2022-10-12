package com.theone.common.ext

import android.app.Activity
import android.app.Application
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.theone.common.callback.AppViewModelProviderOwner
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass


/**
 * TODO 获取Application级别ViewModel
 *
 *
 * 初始化：
 *
 *      Application 实现 AppViewModelProviderOwner :
 *
 *      class YourApp:BaseApplication(),AppViewModelProviderOwner{
 *
 *           private val mAppViewModelProvider: ViewModelProvider by lazy {
 *                 ViewModelProvider(ViewModelStore(), ViewModelProvider.AndroidViewModelFactory(this))
 *           }
 *
 *           override fun getAppViewModelProvider(): ViewModelProvider = mAppViewModelProvider
 *
 *      }
 *
 **/

/**
 *
 *  使用方式 1:
 *
 *      Activity 中使用
 *
 *      val appVm:AppViewModel by appViewModels()
 *
 *      Fragment 中使用
 *
 *      val appVm:AppViewModel by appViewModels(appContext)
 *
 *  使用方式 2:
 *
 *      val appContext:YourApp by lazy { YourApp.app }
 *
 *      class YourApp:BaseApplication(),AppViewModelProviderOwner{
 *
 *               companion object {
 *                   lateinit var app: YourApp
 *               }
 *
 *              onCreate(){
 *                  app = this
 *              }
 *
 *             ......
 *
 *      }
 *
 *      @MainThread
 *      inline fun <reified VM : ViewModel> appViewModels(): Lazy<VM> {
 *          return AppViewModelLazy(VM::class, appContext.getAppViewModelProvider())
 *      }
 *
 *      任意地方使用
 *
 *      val appVm:AppViewModel by appViewModels()
 *
 * */

@MainThread
inline fun <reified VM : ViewModel> appViewModels(appViewModelProvider: AppViewModelProviderOwner): Lazy<VM> {
    return AppViewModelLazy(VM::class, appViewModelProvider.getAppViewModelProvider())
}

@MainThread
inline fun <reified VM : ViewModel> Application.appViewModels(): Lazy<VM> {
    if (this is AppViewModelProviderOwner) {
        return appViewModels(this)
    }
    throw NullPointerException("你的Application没有实现AppViewModelProviderOwner，暂时无法使用getAppViewModel该方法")
}

@MainThread
inline fun <reified VM : ViewModel> Activity.appViewModels(): Lazy<VM> {
    return application.appViewModels()
}

open class AppViewModelLazy<VM : ViewModel>(
    private val viewModelClass: KClass<VM>,
    private val viewModelProvider: ViewModelProvider
) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            return cached
                ?: viewModelProvider[viewModelClass.java].also {
                    cached = it
                }
        }

    override fun isInitialized(): Boolean = cached != null
}

/**
 *
 *  Activity/Fragment中使用
 *
 *  val appVm:AppViewModel by lazy {
 *       getApplicationViewModel()
 *  }
 *
 * */

/**
 * 使用getAppViewModelProvider获取Application上下文的ViewModel
 */
inline fun <reified VM : ViewModel> AppViewModelProviderOwner.getApplicationViewModel(): VM {
    return getAppViewModelProvider()[VM::class.java]
}

/**
 * 获取Application上下文的ViewModel
 */
inline fun <reified VM : ViewModel> Application.getAppViewModel(): VM {
    if (this is AppViewModelProviderOwner) {
        return getApplicationViewModel()
    }
    throw NullPointerException("你的Application没有实现AppViewModelProviderOwner，暂时无法使用getAppViewModel该方法")
}

/**
 * 在Activity中得到Application上下文的ViewModel
 */
inline fun <reified VM : ViewModel> Activity.getAppViewModel(): VM =
    application.getAppViewModel()

/**
 * 在Fragment中得到Application上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : ViewModel> Fragment.getAppViewModel(): VM =
    requireActivity().getAppViewModel()
