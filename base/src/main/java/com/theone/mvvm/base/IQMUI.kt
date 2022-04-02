package com.theone.mvvm.base

import android.view.View
import android.view.ViewGroup
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.mvvm.R
import com.theone.mvvm.entity.ProgressBean

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
 * @date 2021-03-31 14:28
 * @describe 基类相关
 * @email 625805189@qq.com
 * @remark
 */
interface IQMUI {

    /**
     * 获取布局构造器
     * @return ViewConstructor
     */
    fun getViewConstructor(): ViewConstructor

    /**
     * 获取内容层构造工厂
     * @return ViewConstructor.Factory
     */
    fun getContentViewFactory(): ViewConstructor.Factory

    /**
     * 获取布局
     */
    fun getLayoutId(): Int = -1

    /**
     * 根布局
     * @return ViewGroup
     */
    fun getRootView(): ViewGroup = getViewConstructor().getRootView()

    /**
     * 内容层
     * @return View
     */
    fun getContentView(): View = getViewConstructor().getContentView()

    /**
     * 提供一个方法供子类获取TopBar
     */
    fun getTopBar(): QMUITopBarLayout? = getViewConstructor().getTopBar()

    /**
     * 主背景颜色
     * @return Int
     */
    fun getRootBackgroundColor(): Int? = R.attr.app_skin_main_background_color

    /**
     * 是否需要TopBar
     */
    fun showTopBar(): Boolean = true

    /**
     * 布局初始化
     * @param root View
     */
    fun initView(root: View)

    /**
     * 显示加载框
     * @param msg String?
     */
    fun showLoading(msg: String?) {}

    /**
     * 隐藏加载框
     */
    fun hideLoading() {}

    /**
     * 显示进度弹窗
     * @param progress ProgressUI
     */
    fun showProgress(progress: ProgressBean) {}

    /**
     * 隐藏进度弹窗
     */
    fun hideProgress() {}

    /**
     * @return 是否设置状态栏LightMode true 深色图标 false 白色背景
     */
    fun isStatusBarLightMode(): Boolean = true

    /**
     * @return 是否要进行对状态栏的处理
     */
    fun isNeedChangeStatusBarMode(): Boolean = true

    /**
     * true -> 内容层将充满整个屏幕，直接延伸至状态栏
     * false ->内容层将有一个向上的TopBar高度的间距
     */
    fun translucentFull(): Boolean = false

}
