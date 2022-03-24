package com.theone.demo.viewmodel

import com.theone.demo.data.model.bean.ClassifyResponse
import com.theone.demo.data.repository.ApiRepository
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel
import com.theone.demo.app.ext.getCacheMode


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
class WxGzhRequestViewModel : BaseRequestViewModel<List<ClassifyResponse>>() {

    override fun requestServer() {
        request({
            onSuccess(ApiRepository.INSTANCE.getWxGzh(getCacheMode(true)))
        })
    }

}