package com.theone.demo.data.request

import com.theone.demo.data.model.bean.ClassifyResponse
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
 * @date 2022-06-10 10:18
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ProjectRequest:BaseRequest<List<ClassifyResponse>>() {

    suspend fun getProjectList(){
        requestAwait(ApiRepository.INSTANCE.getProjectItems())
    }

}