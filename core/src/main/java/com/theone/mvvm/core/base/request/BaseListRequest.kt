package com.theone.mvvm.core.base.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.mvvm.core.data.net.IPageInfo
import com.theone.mvvm.core.data.net.error.msg
import kotlinx.coroutines.launch
import rxhttp.awaitResult
import rxhttp.wrapper.coroutines.Await

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
 * @date 2022-05-27 09:00
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseListRequest<T>:BaseRequest<List<T>>() {

    /**
     * 分页信息,实体需实现 IPageInfo
     */
    var pageInfo : IPageInfo? = null

    /**
     * 是否第一次加载
     */
    var isFirst: Boolean = true

    /**
     * 是否刷新
     */
    var isFresh: Boolean = false

    /**
     * 开始的页数（有些接口从0开始，有的从1开始）
     */
    var startPage: Int = 1

    /**
     *  当前页面
     */
    var page: Int = startPage

    /**
     * 数据请求成功后调用此方法
     * @param response 返回的数据
     * @param pageInfo 页码信息
     */
    protected fun onSuccess(response: List<T>?, pageInfo: IPageInfo? =null){
        // 这个一定要放前面
        this.pageInfo = pageInfo
        onSuccess(response)
    }

}