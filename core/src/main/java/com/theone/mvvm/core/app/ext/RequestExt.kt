package com.theone.mvvm.core.app.ext

import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel
import com.theone.mvvm.core.data.net.error.msg
import kotlinx.coroutines.*
import rxhttp.awaitResult
import rxhttp.wrapper.coroutines.Await

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
 * @date 2021-12-17 13:29
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

/**
 * @param block     协程代码块，运行在UI线程
 * @param onError   异常回调，运行在UI线程
 * @param onStart   协程开始回调，运行在UI线程
 * @param onFinally 协程结束回调，不管成功/失败，都会回调，运行在UI线程
 */
fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null
): Job {
    return viewModelScope.launch {
        try {
            coroutineScope {
                onStart?.invoke()
                block()
            }
        } catch (e: Throwable) {
            if (onError != null && isActive) {
                try {
                    onError(e)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            } else {
                e.printStackTrace()
            }
        } finally {
            onFinally?.invoke()
        }
    }
}


fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return viewModelScope.launch {
        block()
    }
}


