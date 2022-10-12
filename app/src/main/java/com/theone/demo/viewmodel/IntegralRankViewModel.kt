package com.theone.demo.viewmodel

import com.theone.demo.data.model.bean.IntegralResponse
import com.theone.demo.data.repository.ApiRepository
import com.theone.common.callback.databind.BooleanObservableField


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
 * @date 2021/3/17 0017
 * @describe 积分排行
 * @email 625805189@qq.com
 * @remark
 */
class IntegralRankViewModel:BasePagerViewModel<IntegralResponse>() {

    val showMineRank:BooleanObservableField = BooleanObservableField(false)

    init {
        startPage = 1
    }

    override fun requestServer() {
        request({
            onSuccess(ApiRepository.INSTANCE.getIntegralRank(page,getCacheMode()).await())
            showMineRank.set(true)
        })
    }

}