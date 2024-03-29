package com.theone.demo.data.request

import com.theone.demo.app.ext.getCacheMode
import com.theone.demo.app.ext.getCacheModeOnly
import com.theone.demo.data.model.bean.IntegralResponse
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
 * @date 2022-05-27 09:05
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class MineRequest : BaseRequest<IntegralResponse>() {

    var isFirst: Boolean = true

    suspend fun getUserIntegral() {
        requestAwait(ApiRepository.INSTANCE.getUserCoin(getCacheModeOnly(isFirst)))
    }

}