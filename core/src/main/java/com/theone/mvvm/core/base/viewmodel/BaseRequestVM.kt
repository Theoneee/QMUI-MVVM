package com.theone.mvvm.core.base.viewmodel

import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.request.BaseRequest
import com.theone.mvvm.ext.getClazz

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
 * @date 2022-06-09 09:33
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseRequestVM<R:BaseRequest<*>>:BaseViewModel() {

    private val mRequest:R by lazy {
        createRequest()
    }

    open fun getRequest():R = mRequest

    abstract fun createRequest():R

    abstract fun requestServer()

}