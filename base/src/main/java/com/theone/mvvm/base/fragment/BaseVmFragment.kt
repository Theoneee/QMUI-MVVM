package com.theone.mvvm.base.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.theone.mvvm.base.IViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.ext.addLoadingObserveExt
import com.theone.mvvm.ext.createViewModel
import com.theone.mvvm.ext.getClazz


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
 * @describe 持有 ViewModel Fragment基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseVmFragment<VM : BaseViewModel> : BaseQMUIFragment(),
    IViewModel<VM> {

    private val mViewModel: VM by lazy {
        createViewModel(this)
    }

    override fun getViewModel(): VM = mViewModel

    override fun getViewModelClass(): Class<VM> = getClazz(this, getViewModelIndex())

    /**
     * ViewModel的位置
     */
    override fun getViewModelIndex(): Int = 0

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        addLoadingObserve(getViewModel())
        createObserver()
        initData()
    }

    /**
     * 添加加载观察
     * @param viewModels Array<out BaseViewModel>
     */
    override fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        addLoadingObserveExt(this, *viewModels)
    }

}