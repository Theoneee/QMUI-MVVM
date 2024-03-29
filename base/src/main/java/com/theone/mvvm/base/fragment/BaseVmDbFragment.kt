package com.theone.mvvm.base.fragment

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
 * @date 2021/2/22 0022
 * @describe ViewModel+DataBinding基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>(),
    IDataBinding<DB> {

    override fun getContentViewFactory(): ViewConstructor.Factory =
        ViewConstructor.DataBindingFactory<DB>(getDataBindingClass()) {
            it.initDataBinding(this, this, getViewModel())
        }

    override fun getDataBinding(): DB = (getViewConstructor().getFactory() as ViewConstructor.DataBindingFactory<*>).getDataBinding() as DB

    @Deprecated(message = "View通过DataBinding反射进行创建，这个不再需要", replaceWith = ReplaceWith(""))
    override fun getLayoutId(): Int = 0

}