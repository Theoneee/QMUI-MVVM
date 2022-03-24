package com.theone.mvvm.base.fragment

import androidx.databinding.ViewDataBinding
import com.theone.mvvm.BR
import com.theone.mvvm.base.IDataBinding
import com.theone.mvvm.base.ViewConstructor
import com.theone.mvvm.base.ViewConstructorImpl
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.ext.getClazz
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

    private val factory: ViewConstructor.DataBindingFactory<DB> by lazy {
        ViewConstructor.DataBindingFactory<DB>(getDataBindingClass()){
            it.initDataBinding(this@BaseVmDbFragment,this@BaseVmDbFragment,getViewModel())
        }
    }

    override fun getContentViewFactory(): ViewConstructor.Factory = factory

    override fun getDataBinding(): DB = factory.getDataBinding()

    override fun getDataBindingIndex(): Int = 1

    override fun getDataBindingClass(): Class<DB> = getClazz(this, getDataBindingIndex())

    /**
     * 布局ID
     */
    @Deprecated(message = "View通过DataBinding反射进行创建，这个不再需要", replaceWith = ReplaceWith(""))
    override fun getLayoutId(): Int = 0

    /**
     * 视图里绑定的ViewModel ID
     */
    override fun getBindingVmId(): Int = BR.vm

    /**
     * 视图里绑定的点击事件 ID
     */
    override fun getBindingClickId(): Int = BR.click

    /**
     * 视图里绑定的点击事件
     */
    override fun getBindingClick(): Any? = null

}