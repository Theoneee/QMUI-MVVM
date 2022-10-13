package com.theone.common.ext

import android.app.Activity
import android.app.Application
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.theone.common.util.AppViewModelManager
import com.theone.common.callback.AppViewModelProviderOwner
import kotlin.reflect.KClass


/**
 * TODO 获取Application级别ViewModel
 *
 * Step 1:
 *      在Application里初始化：
 *
 *      CommonManager.init(Application)

 *  Step 2:
 *      任意地方使用
 *
 *      val appVm:AppViewModel by appViewModels()
 * */

@MainThread
inline fun <reified VM : ViewModel> AppViewModelProviderOwner.appViewModels(): Lazy<VM> {
    return AppViewModelLazy(VM::class, this)
}

@MainThread
inline fun <reified VM : ViewModel> appViewModels(): Lazy<VM> {
    return AppViewModelLazy(VM::class, AppViewModelManager.INSTANCE!!)
}

open class AppViewModelLazy<VM : ViewModel>(
    private val viewModelClass: KClass<VM>,
    private val providerOwner: AppViewModelProviderOwner
) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            return cached
                ?: providerOwner.getAppViewModelProvider()[viewModelClass.java].also {
                    cached = it
                }
        }

    override fun isInitialized(): Boolean = cached != null
}

/**
 *  Step 1:
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
 *  Activity/Fragment中使用
 *
 *  val appVm:AppViewModel by lazy {
 *       getApplicationViewModel()
 *  }
 *
 * */

/**
 * 获取Application上下文的ViewModel
 */
@Deprecated("优化省略了lazy方法，此方法已过时", replaceWith = ReplaceWith("请使用appViewModels()替换"))
inline fun <reified VM : ViewModel> Application.getAppViewModel(): VM {
    if (this is AppViewModelProviderOwner) {
        return getAppViewModelProvider()[VM::class.java]
    }else if(null !=AppViewModelManager.INSTANCE){
        return AppViewModelManager.INSTANCE!!.getAppViewModelProvider()[VM::class.java]
    }
    throw NullPointerException("你的Application没有实现AppViewModelProviderOwner，暂时无法使用getAppViewModel该方法")
}

/**
 * 在Activity中得到Application上下文的ViewModel
 */
@Deprecated("优化省略了lazy方法，此方法已过时", replaceWith = ReplaceWith("请使用appViewModels()替换"))
inline fun <reified VM : ViewModel> Activity.getAppViewModel(): VM =
    application.getAppViewModel()

/**
 * 在Fragment中得到Application上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
@Deprecated("优化省略了lazy方法，此方法已过时", replaceWith = ReplaceWith("请使用appViewModels()替换"))
inline fun <reified VM : ViewModel> Fragment.getAppViewModel(): VM =
    requireActivity().getAppViewModel()
