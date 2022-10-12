package com.theone.demo.viewmodel

import com.theone.common.callback.databind.StringObservableField
import com.theone.demo.data.request.AddShareArticleRequest
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.app.ext.request


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
 * @date 2021/3/18 0018
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class AddShareArticleViewModel:BaseViewModel() {

    val title :StringObservableField = StringObservableField("")
    val url :StringObservableField = StringObservableField("")
    val publisher :StringObservableField = StringObservableField("")

    val mRequest = AddShareArticleRequest()

    fun requestServer() {
        request({
            mRequest.requestServer(title.get(),url.get())
        },"提交中")
    }
}