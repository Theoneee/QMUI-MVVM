package com.theone.mvvm.base.activity

import android.os.Bundle
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
 * @date 2021-03-31 15:01
 * @describe 持有 ViewModel Activity基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseQMUIActivity(),
    IViewModel<VM> {

    private val mViewModel: VM by lazy {
        createViewModel(this)
    }

    override fun getViewModel(): VM  = mViewModel

    override fun setContentView(view: View?) {
        super.setContentView(view)
        // 创建观察者
        addLoadingObserve(getViewModel())
        createObserver()
        initData()
    }

    /**
     * 添加加载观察
     * @param viewModels Array<out BaseViewModel>
     */
    override fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        addLoadingObserveExt(this,*viewModels)
    }

}