package com.theone.mvvm.core.base.callback

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.theone.mvvm.core.data.enum.LayoutManagerType

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
 * @date 2021-03-25 10:03
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface IRecyclerPager<T> {

    fun getRecyclerView(): RecyclerView

    fun getAdapter():BaseQuickAdapter<T,*>

    /**
     * 初始化适配器
     */
    fun initAdapter()

    /**
     * 获取适配器的加载更多视图
     * @return BaseLoadMoreView
     */
    fun getAdapterLoadMoreView():BaseLoadMoreView

    /**
     * 初始化RecyclerView
     */
    fun initRecyclerView()

    /**
     * 初始化下拉刷新
     */
    fun initRefreshView(){}

    /**
     * 设置刷新控制状态
     * @param enabled Boolean
     */
    fun setRefreshLayoutEnabled(enabled:Boolean){}

    /**
     * 直接调用刷新
     */
    fun onRefreshDirectly(){}

    /**
     * 是否需要懒加载数据
     * @return Boolean
     */
    fun isLazyLoadData():Boolean

    /**
     * 请求服务器数据
     */
    fun requestServer()

    /**
     * 第一次显示时的加载情况
     */
    fun onFirstLoading()

    /**
     * 第一次加载数据完成
     */
    fun onFirstLoadSuccess(data:List<T>)

    /**
     * 第一次加载数据失败
     */
    fun onFirstLoadError(errorMsg:String?)

    /**
     * 刷新时的操作
     */
    fun onRefresh()

    /**
     * 回到界面自动刷新，刷新前可能是空白页，也可能存在数据
     */
    fun onAutoRefresh()

    /**
     * 刷新完成
     */
    fun onRefreshSuccess(data:List<T>)

    /**
     * 刷新失败
     */
    fun onRefreshError(errorMsg:String?)

    /**
     * 空数据
     */
    fun onEmptyData()

    /**
     * 加载更多
     */
    fun onLoadMore()

    /**
     * 加载更多失败
     */
    fun onLoadMoreSuccess(data:List<T>)

    /**
     * 加载更多失败
     */
    fun onLoadMoreError(errorMsg:String?)

    /**
     * 加载更多完成，还有数据
     */
    fun onLoadMoreComplete()

    /**
     * 没有更多了
     */
    fun onLoadMoreEnd()

    /**
     * 获取LayoutManager
     */
    fun getLayoutManager(): RecyclerView.LayoutManager

    /**
     * 获取LayoutManager
     */
    fun getLayoutManagerType(): LayoutManagerType

    /**
     * 分割线
     */
    fun getItemDecoration(): RecyclerView.ItemDecoration

    /**
     * 获取列数
     */
    fun getSpanCount(): Int

    /**
     * 获取间距大小
     */
    fun getItemSpace(): Int

}