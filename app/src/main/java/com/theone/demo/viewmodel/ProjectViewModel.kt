package com.theone.demo.viewmodel

import com.theone.demo.data.model.bean.ClassifyResponse
import com.theone.demo.data.repository.ApiRepository
import com.theone.demo.data.request.ProjectRequest
import com.theone.demo.data.request.WxGzhRequest
import com.theone.mvvm.core.app.ext.request
import com.theone.mvvm.core.base.viewmodel.BaseRequestVM
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel


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
class ProjectViewModel : BaseRequestVM<ProjectRequest>() {

    var isReload = false

    val wxRequest: WxGzhRequest  = WxGzhRequest()

    override fun requestServer() {
        request({
            if(isReload){
                wxRequest.getWxGzhItems()
            }else{
                getRequest().getProjectList()
            }
        })
    }

    override fun createRequest() = ProjectRequest()

}