package com.theone.demo.data.request

import com.theone.demo.data.repository.ApiRepository
import com.theone.mvvm.core.base.request.BaseRequest

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
 * @date 2022-06-09 08:52
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class AddShareArticleRequest : BaseRequest<String>() {

    suspend fun requestServer(vararg params: Any) {
        requestAwait(ApiRepository.INSTANCE.shareArticle(params[0] as String, params[0] as String))
    }

}