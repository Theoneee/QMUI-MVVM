package com.theone.mvvm.base.activity

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
 * @date 2021-03-31 15:18
 * @describe ViewModel+DataBinding基类
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>(),
    IDataBinding<DB> {

    private val factory: ViewConstructor.DataBindingFactory<DB> by lazy {
        ViewConstructor.DataBindingFactory<DB>(getDataBindingClass()){
            it.initDataBinding(this@BaseVmDbActivity,this@BaseVmDbActivity,getViewModel())
        }
    }

    override fun getContentViewFactory(): ViewConstructor.Factory = factory

    override fun getDataBinding(): DB = factory.getDataBinding()

    override fun getDataBindingIndex(): Int  = 1

    override fun getDataBindingClass(): Class<DB> = getClazz(this,getDataBindingIndex())

    /**
     * 布局ID
     * @return Int
     */
    @Deprecated(message = "DataBinding通过泛型反射进行创建，这个不再需要", replaceWith = ReplaceWith(""))
    override fun getLayoutId(): Int = 0

    /**
     * @return Int  视图里绑定的ViewModel ID
     * @remark 如果使用默认值，则都要命名为 vm ,如果不一致,重写此方法返回
     */
    override fun getBindingVmId(): Int = BR.vm

    /**
     *
     * @return Int 视图里绑定的点击事件 ID
     */
    override fun getBindingClickId(): Int = BR.click

    /**
     * 视图里绑定的点击事件
     * @return Int
     */
    override fun getBindingClick(): Any? = null

}