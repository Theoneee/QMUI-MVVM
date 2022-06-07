package com.theone.mvvm.core.base.request

import androidx.lifecycle.LiveData
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.theone.mvvm.core.data.entity.ErrorInfo

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
interface IRequest<T> {

    suspend fun requestServer(vararg params:Any)

    fun getResponseLiveData():LiveData<T>
    fun getErrorLiveData(): LiveData<ErrorInfo>
    fun getStateLiveData():LiveData<Boolean>

}