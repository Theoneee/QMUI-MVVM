package com.theone.demo.app.net

import com.google.gson.annotations.SerializedName
import com.theone.mvvm.core.base.request.IResponse

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
 * @date 2021/3/2 0002
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
data class Response<T>(
    val errorCode: Int,
    val errorMsg: String?,
    val data: T?
) : IResponse<T> {

    override fun isSuccess(): Boolean = errorCode == 0

    override fun getResponse(): T? = data

    override fun getMsg(): String? = errorMsg

    override fun getCode(): Int = errorCode
}