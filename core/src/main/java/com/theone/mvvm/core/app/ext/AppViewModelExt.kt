package com.theone.mvvm.core.app.ext

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.theone.common.ext.AppViewModelLazy
import com.theone.mvvm.core.app.appContext


@MainThread
inline fun <reified VM : ViewModel> appViewModels(): Lazy<VM> {
    return AppViewModelLazy(VM::class, appContext.getAppViewModelProvider())
}

