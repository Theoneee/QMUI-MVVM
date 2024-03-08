package com.theone.mvvm.base

import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.theone.mvvm.BR
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
 * @date 2021-03-31 15:04
 * @describe DataBinding基类相关
 * @email 625805189@qq.com
 * @remark
 */
interface IDataBinding<DB:ViewDataBinding> {

    /**
     * 获取DataBinding，供子类调用
     * @return DB
     */
    fun getDataBinding():DB

    /**
     * DataBinding在泛型中的位置
     * @return Int
     * @remark 如果子类中没有了DB泛型，则需要重写[getDataBindingClass]直接指定Class
     *         例：很多界面都用的是一个DB，那么可以指定这个DB进行封装，子类重写后是没有DB的
     */
    fun getDataBindingIndex():Int = 1

    /**
     * DataBinding的class
     * @return Class<*>
     */
    fun getDataBindingClass():Class<DB> = getClazz(getDataBindingIndex())

    /**
     * 视图绑定里ViewModel的ID
     * @return Int
     */
    fun getBindingVmId():Int = BR.vm

    /**
     * 视图绑定里Click的ID
     * @return Int
     */
    fun getBindingClickId(): Int = BR.click

    /**
     * 视图绑定里的Click
     * @return Any?
     */
    fun getBindingClick(): Any? = null

    /**
     * 向DataBinding注入值
     */
    fun DB.applyBindingParams(){}

}
