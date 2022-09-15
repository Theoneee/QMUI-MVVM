package com.theone.mvvm.base

import android.util.SparseArray
import androidx.annotation.NonNull
import androidx.viewbinding.ViewBinding
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
interface IViewBinding<VB:ViewBinding> {

    /**
     * 获取ViewBinding，供子类调用
     * @return DB
     */
    fun getViewBinding():VB

    /**
     * ViewBinding在泛型中的位置
     * @return Int
     * @remark 如果子类中没有了VB泛型，则需要重写[getViewBindingClass]直接指定Class
     *         例：很多界面都用的是一个DB，那么可以指定这个VB进行封装，子类重写后是没有VB的
     */
    fun getViewBindingIndex():Int = 0

    /**
     * DataBinding的class
     * @return Class<*>
     */
    fun getViewBindingClass():Class<VB> = getClazz()


}
