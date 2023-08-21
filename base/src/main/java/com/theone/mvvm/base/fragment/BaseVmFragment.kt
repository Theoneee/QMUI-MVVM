package com.theone.mvvm.base.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.theone.mvvm.base.IViewModel
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.ext.addLoadingObserveExt


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
        createViewModel()
    }

    override fun createViewModel(): VM  = ViewModelProvider(this)[getViewModelClass()]

    override fun getViewModel(): VM = mViewModel

    override fun onViewCreated(rootView: View) {
        // 放在[initView]之前，不然错误的在[initView]方法里请求了，响应太快[createObserver]都没还没执行
        addLoadingObserve(getViewModel())
        createObserver()
        super.onViewCreated(rootView)
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