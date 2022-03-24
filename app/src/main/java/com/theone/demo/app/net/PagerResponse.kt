package com.theone.demo.app.net

import com.theone.mvvm.core.data.net.IPageInfo
import java.io.Serializable

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
class PagerResponse<T> : IPageInfo, Serializable {
    var curPage = 0
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: T? = null

    override fun getPage(): Int {
        return curPage
    }

    override fun getPageTotalCount(): Int {
        return pageCount
    }

    override fun getTotalCount(): Int {
        return total
    }

    override fun getPageSize(): Int {
        return size
    }
}