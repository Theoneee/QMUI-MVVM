package com.theone.demo.viewmodel

import com.theone.demo.data.request.WxGzhRequest
import com.theone.mvvm.core.app.ext.request
import com.theone.mvvm.core.base.viewmodel.BaseRequestVM


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
 * @describe 微信公众号
 * @email 625805189@qq.com
 * @remark
 */
class WxGzhRequestViewModel : BaseRequestVM<WxGzhRequest>() {

    override fun requestServer() {
        request({
            getRequest().getWxGzhItems()
        })
    }

    override fun createRequest() = WxGzhRequest()

}