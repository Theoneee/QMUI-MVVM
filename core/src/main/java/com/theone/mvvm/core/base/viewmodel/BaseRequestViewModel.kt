package com.theone.mvvm.core.base.viewmodel

import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.app.ext.launch
import com.theone.mvvm.core.data.net.error.msg
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
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
 * @date 2021/2/25 0025
 * @describe 请求ViewModel基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseRequestViewModel<T> : BaseViewModel() {

    abstract fun requestServer()

    /**
     * 请求返回的数据
     */
    private val response: UnPeekLiveData<T> =
        UnPeekLiveData.Builder<T>().setAllowNullValue(true).create()

    /**
     * 错误原因
     */
    private val error: UnPeekLiveData<String> =
        UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()

    /**
     * 请求无论成功或者失败之后的回调
     */
    private val finally: UnPeekLiveData<Boolean> = UnPeekLiveData()

    /**
     * 向 ui 层提供 ProtectedUnPeekLiveData，从而限制从 Activity/Fragment 篡改来自 "数据层" 的数据，数据层的数据务必通过 "唯一可信源" 来分发
     * from: KunMinX  https://xiaozhuanlan.com/topic/0168753249
     */
    fun getResponseLiveData(): ProtectedUnPeekLiveData<T> = response
    fun getErrorLiveData(): ProtectedUnPeekLiveData<String> = error
    fun getFinallyLiveData(): ProtectedUnPeekLiveData<Boolean> = finally

    /***  下面的方法不向外曝露，只供内部使用，保证数据只能从ViewModel到UI层  **/

    /**
     * 请求成功后设置数据调用此方法
     * @param response
     */
    protected fun onSuccess(response: T?) {
        this.response.value = response
    }

    /**
     * 请求错误时调用此方法
     * @param errorMsg 错误信息
     * @param errorLiveData 错误接收的LiveData
     */
    protected fun onError(errorMsg: String?, errorLiveData: UnPeekLiveData<String>? = null) {
        (errorLiveData ?: error).value = errorMsg
    }

    protected fun onFinally() {
        finally.value = true
    }

    /**
     * 请求
     * @param block 请求的主函数体，得到数据后调用onSuccess方法
     * @param loadingMsg 请求时的提示语句，不为空时才开启弹窗提示
     * @param errorLiveData 接收错误的LiveData
     */
    protected fun request(
        block: suspend CoroutineScope.() -> Unit,
        loadingMsg: String? = null,
        errorLiveData: UnPeekLiveData<String>? = null
    ) {
        launch({
            block()
        }, {
            // 错误回调
            // 这里可以给不同的请求设置不同的错误接收
            it.printStackTrace()
            onError(it.msg, errorLiveData)
        }, {
            // 请求开始
            // 求时的提示语句不为空时才开启弹窗提示
            loadingMsg?.let {
                showLoadingDialog(it)
            }
        }, {
            // 请求结束
            onFinally()
            // 关闭loading
            loadingMsg?.let {
                hideLoadingDialog()
            }
        }
        )
    }

    protected fun requestAwait(
        wait: Await<T>,
        loadingMsg: String? = null,
        errorLiveData: UnPeekLiveData<String>? = null
    ) {
        viewModelScope.launch {
            // 请求开始
            // 求时的提示语句不为空时才开启弹窗提示
            loadingMsg?.let {
                showLoadingDialog(it)
            }
            wait.awaitResult { result ->
                onSuccess(result)
            }.onFailure {
                // 错误回调
                // 这里可以给不同的请求设置不同的错误接收
                it.printStackTrace()
                onError(it.msg, errorLiveData)
            }
            // 关闭loading
            loadingMsg?.let {
                hideLoadingDialog()
            }
            // 请求结束
            onFinally()
        }
    }

    protected fun requestFlow(
        flow: Flow<T>,
        loadingMsg: String?,
        errorLiveData: UnPeekLiveData<String>? = error
    ) {
        viewModelScope.launch {
            flow.catch {
                onError(it.msg, errorLiveData)
            }.onStart {
                // 请求开始
                // 求时的提示语句不为空时才开启弹窗提示
                loadingMsg?.let {
                    showLoadingDialog(it)
                }
            }.collect {
                onSuccess(it)
            }
        }
    }

}
