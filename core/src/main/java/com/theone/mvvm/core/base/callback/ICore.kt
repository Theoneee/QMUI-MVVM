package com.theone.mvvm.core.base.callback

import android.view.View
import com.theone.mvvm.core.base.loader.LoaderService
import com.theone.mvvm.core.base.loader.callback.Callback

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
 * @date 2021-04-29 10:45
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface ICore {

    /**
     * 界面状态管理
     * @return LoaderView?
     */
    fun getLoader(): LoaderService? = null

    /**
     * 界面状态注册View, 显示的Loading界面将使用此界面的[LayoutParams]
     * 为null将不会注册[LoaderService]
     * @return View?
     */
    fun loaderRegisterView():View? = null

    /**
     * 默认的状态
     * @return LoaderStatus
     */
    fun loaderDefaultCallback():Class<out Callback>? = null

    /**
     * 错误、空界面重新
     */
    fun onPageReLoad() {}

    /**
     * 是否为退出界面
     * @return Boolean
     */
    fun isExitPage():Boolean = false

    /**
     * 显示退出提示
     */
    fun showExitTips()

}