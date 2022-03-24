package com.theone.mvvm.core.app.ext

import android.content.Context
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.theone.mvvm.core.data.enum.LayoutManagerType
import com.theone.mvvm.core.base.fragment.BasePagerAdapterFragment
import com.theone.mvvm.core.base.callback.IRecyclerPager
import com.theone.mvvm.core.base.viewmodel.BaseListViewModel

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
 * @date 2021-03-25 09:38
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun RecyclerView.init(
    manager: RecyclerView.LayoutManager,
    mAdapter: BaseQuickAdapter<*, *>? = null,
    decoration: RecyclerView.ItemDecoration? = null
) {
    layoutManager = manager
    mAdapter?.let {
        adapter = it
    }
    decoration?.let {
        addItemDecoration(it)
    }
}

fun Context.createLayoutManager(
    type: LayoutManagerType = LayoutManagerType.LIST,
    spanCount: Int = 2
): RecyclerView.LayoutManager {
    return when (type) {
        LayoutManagerType.GRID -> GridLayoutManager(this, spanCount)
        LayoutManagerType.STAGGERED ->
            StaggeredGridLayoutManager(
                spanCount,
                StaggeredGridLayoutManager.VERTICAL
            ).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        else -> LinearLayoutManager(this)
    }
}

fun <T, VM : BaseListViewModel<T>> BasePagerAdapterFragment<T, VM, *>.createListVmObserve() {
    getViewModel().run {
        getResponseLiveData().observe(this@createListVmObserve, Observer {
            loadListData(this)
        })
        getErrorLiveData().observe(this@createListVmObserve, Observer {
            loadListError(this)
        })
    }
}


/**
 * List数据请求成功后设置数据的统一封装
 * @param vm        数据源
 * @param adapter   适配器
 * @param loader    界面管理器
 */
fun <T> IRecyclerPager<T>.loadListData(
    vm: BaseListViewModel<T>
) {
    val list = vm.getResponseLiveData().value
    val isNewData = vm.page == vm.startPage
    if (list.isNullOrEmpty()) {
        if (isNewData) {
            onEmptyData()
        } else {
            onLoadMoreEnd()
        }
        return
    }
    if (isNewData) {
        if (vm.isFirst) {
            onFirstLoadSuccess(list)
            vm.isFirst = false
        } else {
            onRefreshSuccess(list)
            vm.isFresh = false
        }
    } else {
        onLoadMoreSuccess(list)
    }
    val pageInfo = vm.pageInfo
    if (pageInfo == null || pageInfo.getPageTotalCount() > pageInfo.getPage()) {
        vm.page++
        onLoadMoreComplete()
    } else {
        onLoadMoreEnd()
    }
}

/**
 * 请求失败时
 * @param vm        数据源
 * @param adapter   适配器
 * @param loader    界面管理器
 */
fun <T> IRecyclerPager<T>.loadListError(
    vm: BaseListViewModel<T>
) {
    with(vm.getErrorLiveData().value) {
        when {
            vm.isFirst -> {
                onFirstLoadError(this)
            }
            vm.isFresh -> {
                onRefreshError(this)
            }
            else -> {
                onLoadMoreError(this)
            }
        }
    }
}


