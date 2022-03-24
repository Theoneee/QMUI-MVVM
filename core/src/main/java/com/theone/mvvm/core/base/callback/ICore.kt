package com.theone.mvvm.core.base.callback

import android.view.View
import com.theone.mvvm.core.app.widge.loadsir.core.LoadService

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
     * 状态用来注册View
     * @return View
     */
    fun loadSirRegisterView():View?

    fun getLoadSir(): LoadService<Any>?

    /**
     * 是否为退出界面
     * @return Boolean
     */
    fun isExitPage():Boolean

    /**
     * 显示退出提示
     */
    fun showExitTips()

    /**
     * 错误、空界面重新
     */
    fun onPageReLoad()

}