package com.theone.mvvm.base.activity

import android.os.Bundle
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
 * @date 2021-03-31 15:01
 * @describe 持有 ViewModel Activity基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseQMUIActivity(),
    IViewModel<VM> {

    private val mViewModel: VM by lazy {
        createViewModel()
    }

    override fun createViewModel(): VM  = ViewModelProvider(this)[getViewModelClass()]

    override fun getViewModel(): VM  = mViewModel

    override fun setContentView(view: View?) {
        super.setContentView(view)
        // 放在[initView]之前，不然错误的在[initView]方法里请求了，响应太快[createObserver]都没还没执行
        addLoadingObserve(getViewModel())
        createObserver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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