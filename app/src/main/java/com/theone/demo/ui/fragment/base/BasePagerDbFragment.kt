package com.theone.demo.ui.fragment.base

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.theone.demo.viewmodel.BasePagerViewModel
import com.theone.mvvm.core.databinding.BaseSwipeRefreshFragmentBinding
import com.theone.mvvm.core.databinding.BaseTabInTitleLayoutBinding

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
 * @date 2021-08-09 15:43
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BasePagerDbFragment<T, VM : BasePagerViewModel<T>>:BasePagerListFragment<T,VM, BaseSwipeRefreshFragmentBinding>() {

    override fun getDataBindingClass(): Class<BaseSwipeRefreshFragmentBinding> = BaseSwipeRefreshFragmentBinding::class.java

    override fun getRecyclerView(): RecyclerView = getDataBinding().recyclerView

    override fun getRefreshLayout(): SwipeRefreshLayout? = getDataBinding().swipeRefresh

}