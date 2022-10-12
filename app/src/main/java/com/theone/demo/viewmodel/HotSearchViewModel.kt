package com.theone.demo.viewmodel

import com.theone.demo.data.model.bean.SearchResponse
import com.theone.demo.data.repository.ApiRepository
import com.theone.mvvm.core.base.viewmodel.BaseRequestViewModel
import com.theone.common.callback.databind.BooleanObservableField
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
 * @date 2021/3/11 0011
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HotSearchViewModel:BaseRequestViewModel<List<SearchResponse>>() {

     val isFirst:BooleanObservableField = BooleanObservableField(true)

    override fun requestServer() {
        requestAwait(ApiRepository.INSTANCE.getHotKeys(getCacheMode(isFirst.get())))
    }

}