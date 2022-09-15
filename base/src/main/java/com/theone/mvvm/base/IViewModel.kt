package com.theone.mvvm.base

import com.theone.mvvm.base.viewmodel.BaseViewModel
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
 * @describe ViewModel基类相关
 * @email 625805189@qq.com
 * @remark
 */
interface IViewModel<VM : BaseViewModel> {

    /**
     * 获取ViewModel，供子类调用
     * @return VM
     */
    fun getViewModel(): VM

    /**
     * ViewMode在泛型中的的位置
     */
    /**
     * ViewMode在泛型中的的位置
     * @return Int
     * @remark 如果子类中没有了[VM]泛型，则需要重写[getViewModelClass]直接指定Class
     *         例：很多界面都用的是一个[VM]，那么可以指定这个[VM]进行封装，子类重写后是没有[VM]的
     */
    fun getViewModelIndex(): Int = 0

    /**
     * ViewModel的Class类型
     * @return Class<VM>
     */
    fun getViewModelClass(): Class<VM> = getClazz(getViewModelIndex())

    /**
     * 初始化数据、请求相关的在这个方法里进行，请勿在[initView]
     */
    fun initData() {}

    /**
     * 创建观察者
     */
    fun createObserver() {}

    /**
     * BaseViewModel添加Loading观察
     * @param viewModels Array<out BaseViewModel>
     */
    fun addLoadingObserve(vararg viewModels: BaseViewModel) {}

}
