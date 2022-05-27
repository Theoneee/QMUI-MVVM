package com.theone.mvvm.core.base.request

import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.mvvm.core.data.net.error.msg
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
 * @date 2022-05-27 09:00
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseRequest<T> : IRequest<IResponse<T>> {

    /**
     * 请求返回的数据
     */
    private val response: UnPeekLiveData<IResponse<T>> =
        UnPeekLiveData.Builder<IResponse<T>>().setAllowNullValue(true).create()

    /**
     * 错误原因
     */
    private val error: UnPeekLiveData<String> =
        UnPeekLiveData.Builder<String>().setAllowNullValue(true).create()

    /**
     * 状态回调 true 开始 false 结束
     */
    private val state: UnPeekLiveData<Boolean> = UnPeekLiveData()

    override fun getResponseLiveData(): ProtectedUnPeekLiveData<IResponse<T>> = response

    override fun getErrorLiveData(): ProtectedUnPeekLiveData<String> = error

    override fun getStateLiveData(): ProtectedUnPeekLiveData<Boolean> = state

    /**
     * 请求成功后设置数据调用此方法
     * @param response
     */
    protected fun onSuccess(response: IResponse<T>?) {
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

    protected suspend fun requestAwait(
        wait: Await<IResponse<T>>,
        errorLiveData: UnPeekLiveData<String>? = null
    ) {
        state.value = true
        wait.awaitResult { result ->
            onSuccess(result)
        }.onFailure {
            // 错误回调
            it.printStackTrace()
            onError(it.msg, errorLiveData)
        }
        // 请求结束
        state.value = false
    }

}