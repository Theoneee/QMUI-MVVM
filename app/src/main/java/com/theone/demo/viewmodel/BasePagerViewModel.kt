package com.theone.demo.viewmodel

import com.theone.demo.data.net.PagerResponse
import com.theone.mvvm.core.base.viewmodel.BaseListViewModel
import rxhttp.wrapper.cache.CacheMode


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
 * @date 2021/2/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BasePagerViewModel<T> : BaseListViewModel<T>() {

    var isCache = true

    init {
        startPage = 0
    }

    protected open fun onSuccess(response: PagerResponse<List<T>>?) {
        onSuccess(response?.datas, response)
    }

    open fun getCacheMode(): CacheMode {
        return when {
            isFirst -> if(isCache) CacheMode.ONLY_CACHE else CacheMode.NETWORK_SUCCESS_WRITE_CACHE
            isFresh -> CacheMode.NETWORK_SUCCESS_WRITE_CACHE
            else -> CacheMode.ONLY_NETWORK
        }
    }

}


