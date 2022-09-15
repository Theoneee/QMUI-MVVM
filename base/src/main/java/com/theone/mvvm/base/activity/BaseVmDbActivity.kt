package com.theone.mvvm.base.activity

import androidx.databinding.ViewDataBinding
import com.theone.mvvm.base.IDataBinding
import com.theone.mvvm.base.ViewConstructor
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.ext.initDataBinding

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
 * @date 2021-03-31 15:18
 * @describe ViewModel+DataBinding基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>(),
    IDataBinding<DB> {

    private val factory: ViewConstructor.DataBindingFactory<DB> by lazy {
        ViewConstructor.DataBindingFactory<DB>(getDataBindingClass()){
            it.initDataBinding(this,this,getViewModel())
        }
    }

    override fun getContentViewFactory(): ViewConstructor.Factory = factory

    override fun getDataBinding(): DB = factory.getDataBinding()

    @Deprecated(message = "DataBinding通过泛型反射进行创建，这个不再需要", replaceWith = ReplaceWith(""))
    override fun getLayoutId(): Int = 0

}