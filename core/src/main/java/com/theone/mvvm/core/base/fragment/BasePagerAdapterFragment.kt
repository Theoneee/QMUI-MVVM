package com.theone.mvvm.core.base.fragment

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.theone.common.ext.dp2px
import com.theone.common.widget.TheSpaceItemDecoration
import com.theone.mvvm.core.app.ext.createListVmObserve
import com.theone.mvvm.core.app.ext.init
import com.theone.mvvm.core.app.ext.showSuccessPage
import com.theone.mvvm.core.base.viewmodel.BaseListViewModel
import com.theone.mvvm.core.app.widge.TheLoadMoreView

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
 * @date 2021/2/23 0023
 * @describe 给定了默认的Adapter初始化和RecyclerView初始化, 需要自定义下拉刷新控件继承此类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BasePagerAdapterFragment
<T, VM : BaseListViewModel<T>, DB : ViewDataBinding>
    : BasePagerRecyclerViewFragment<T, VM, DB>(), OnLoadMoreListener, OnItemClickListener {

    private val mAdapter: BaseQuickAdapter<T, *> by lazy {
        createAdapter()
    }

    override fun getAdapter(): BaseQuickAdapter<T, *> = mAdapter

    abstract fun createAdapter(): BaseQuickAdapter<T, *>

    override fun createObserver() {
        createListVmObserve()
    }

    /**
     * 初始化默认的适配器配置
     * 如有需要，重写此方法进行更改
     */
    override fun initAdapter() {
        getAdapter().run {
            if (this is LoadMoreModule) {
                loadMoreModule.loadMoreView = getAdapterLoadMoreView()
                loadMoreModule.setOnLoadMoreListener(this@BasePagerAdapterFragment)
            }
            setOnItemClickListener(this@BasePagerAdapterFragment)
        }
    }

    override fun getAdapterLoadMoreView(): BaseLoadMoreView = TheLoadMoreView()

    override fun initRecyclerView() {
        getRecyclerView().init(getLayoutManager(), getAdapter(), getItemDecoration())
    }

    /**
     * 给一个默认的间距配置
     * @return RecyclerView.ItemDecoration
     */
    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return TheSpaceItemDecoration(
            getSpanCount(),
            dp2px(getItemSpace()),
            getAdapter().headerLayoutCount
        )
    }

    /**
     * 第一次加载数据成功
     * @param data List<T>
     */
    override fun onFirstLoadSuccess(data: List<T>) {
        onRefreshSuccess(data)
        showSuccessPage()
    }

    /**
     * 刷新成功
     * @param data List<T>
     */
    override fun onRefreshSuccess(data: List<T>) {
        getAdapter().setList(data)
        setRefreshLayoutEnabled(true)
        getRecyclerView().scrollToPosition(0)
    }

    /**
     * 加载更多成功
     * @param data List<T>
     */
    override fun onLoadMoreSuccess(data: List<T>) {
        getAdapter().addData(data)
    }

    /**
     * 数据加载后，当前的页数<总页数时会触发此方法
     */
    override fun onLoadMoreComplete() {
        if(getAdapter() is LoadMoreModule){
            getAdapter().loadMoreModule.loadMoreComplete()
        }
    }

    /**
     * 加载更多失败
     * @param errorMsg String?
     */
    override fun onLoadMoreError(errorMsg: String?) {
        if(getAdapter() is LoadMoreModule){
            getAdapter().loadMoreModule.loadMoreFail()
        }
    }

    /**
     * 当没有数据时会触发此方法
     */
    override fun onLoadMoreEnd() {
        if(getAdapter() is LoadMoreModule) {
            getAdapter().loadMoreModule.loadMoreEnd()
        }
    }

}