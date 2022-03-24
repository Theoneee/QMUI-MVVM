package com.theone.mvvm.base.activity

import android.os.Bundle
import com.qmuiteam.qmui.arch.QMUIActivity
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.mvvm.base.ViewConstructor
import com.theone.mvvm.base.IQMUI
import com.theone.mvvm.base.ViewConstructorImpl
import com.theone.mvvm.ext.qmui.*

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
 * @date 2021-03-31 14:26
 * @describe TopBar+状态栏等的封装
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseQMUIActivity : QMUIActivity(), IQMUI {

    protected val TAG: String = this.javaClass.simpleName

    private val mViewConstructor: ViewConstructor by lazy {
        ViewConstructorImpl(this,getContentViewFactory(),showTopBar())
    }

    override fun getViewConstructor(): ViewConstructor = mViewConstructor

    override fun getContentViewFactory(): ViewConstructor.Factory {
        return ViewConstructor.DefaultFactory(getLayoutId())
    }

    /**
     * 提供一个方法供子类获取TopBar
     */
    override fun getTopBar(): QMUITopBarLayout? = getViewConstructor().getTopBar()

    /**
     * 是否需要TopBar
     * 子类重写此方法进行修改
     */
    override fun showTopBar(): Boolean = true

    /**
     * @return 是否设置状态栏LightMode true 深色图标 false 白色背景
     * @remark 根据自己APP的配色，给定一个全局的默认模式。
     *         建议用TopBar的背景颜色做判断。或者在自己的BaseFragment里提供一个全局默认的模式。
     */
    override fun isStatusBarLightMode(): Boolean = true

    /**
     * @return 是否要进行对状态栏的处理
     */
    override fun isNeedChangeStatusBarMode(): Boolean = true


    /**
     * true -> 内容层将充满整个屏幕，直接延伸至状态栏
     *
     * false ->内容层将有一个向上的TopBar高度的间距
     */
    protected open fun translucentFull(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewConstructor().createView(translucentFull()).let {
            setContentView(it)
            initView(it)
        }
    }

    /**
     * 显示加载框
     * @param msg String? 提示语
     * @remark 这了提供了默认的加载效果，如果需要更改，重写此方法以及[hideLoading]
     */
    override fun showLoading(msg: String?) {
        showLoadingDialog(msg)
    }

    /**
     * 隐藏加载框
     */
    override fun hideLoading() {
        hideLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        updateStatusBarMode(isStatusBarLightMode())
    }

}