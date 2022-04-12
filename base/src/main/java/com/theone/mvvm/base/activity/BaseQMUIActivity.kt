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
        ViewConstructorImpl(this,getContentViewFactory(),this)
    }

    override fun getViewConstructor(): ViewConstructor = mViewConstructor

    override fun getContentViewFactory(): ViewConstructor.Factory {
        return ViewConstructor.DefaultFactory(getLayoutId())
    }

    /**
     * 提供一个方法供子类获取TopBar
     */
    override fun getTopBar(): QMUITopBarLayout? = getViewConstructor().getTopBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewConstructor().createView().let {
            setContentView(it)
            getTopBar()?.initTopBar()
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