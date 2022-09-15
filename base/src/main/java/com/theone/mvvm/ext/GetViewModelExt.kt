package com.theone.mvvm.ext

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.theone.mvvm.base.AppViewModelProviderOwner
import com.theone.mvvm.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType


/**
 * 获取当前类绑定的泛型clazz
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.getClazz(index: Int = 0): T {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as T
}

/**
 * 在Activity中得到Application上下文的ViewModel
 */
inline fun <reified VM : BaseViewModel> Application.getAppViewModel(): VM {
    if (this is AppViewModelProviderOwner) {
        return getAppViewModelProvider()[VM::class.java]
    }
    throw NullPointerException("你的Application没有实现AppViewModelProviderOwner，暂时无法使用getAppViewModel该方法")
}

/**
 * 在Activity中得到Application上下文的ViewModel
 */
inline fun <reified VM : BaseViewModel> AppCompatActivity.getAppViewModel(): VM =
    application.getAppViewModel()

/**
 * 在Fragment中得到Application上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> Fragment.getAppViewModel(): VM =
    requireActivity().application.getAppViewModel()







