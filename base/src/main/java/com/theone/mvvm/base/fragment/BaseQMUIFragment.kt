package com.theone.mvvm.base.fragment

import android.animation.Animator
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.qmuiteam.qmui.arch.QMUIFragment
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
 * @date 2021/2/22 0022
 * @describe BaseFragment
 * @email 625805189@qq.com
 * @remark 懒加载+TopBar+状态栏等的封装
 */
abstract class BaseQMUIFragment : QMUIFragment(), IQMUI {

    protected val TAG: String = this.javaClass.simpleName

    lateinit var mActivity: AppCompatActivity

    /**
     * 是否为根Fragment： getParentFragment() == null
     * 可作为一些默认情况的判断依据
     */
    private var isIndexFragment = false

    /**
     * 是否第一次加载
     */
    private var mIsFirstLayInit = true

    private val mViewConstructor: ViewConstructor by lazy {
        ViewConstructorImpl(mActivity, getContentViewFactory(), this)
    }

    override fun getViewConstructor(): ViewConstructor = mViewConstructor

    override fun getContentViewFactory(): ViewConstructor.Factory =
        ViewConstructor.DefaultFactory(getLayoutId())

    /**
     * 是否需要TopBar(默认为根Fragment才需要)
     * 子类重写此方法进行修改
     */
    override fun showTopBar(): Boolean = isIndexFragment

    /**
     * @return 是否要进行对状态栏的处理
     */
    override fun isNeedChangeStatusBarMode(): Boolean  = isIndexFragment

    /**
     * 提供一个方法供子类获取TopBar
     */
    override fun getTopBar(): QMUITopBarLayout? = getViewConstructor().getTopBar()

    override fun onCreateView(): View = getViewConstructor().createView()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
        isIndexFragment = null == parentFragment
    }

    override fun onViewCreated(rootView: View) {
        mIsFirstLayInit = true
        getTopBar()?.initTopBar()
        initView(rootView)
    }

    /**
     * 懒加载
     */
    protected open fun onLazyInit() {}

    /**
     * 界面对用户可见状态
     */
    protected open fun onLazyResume() {
        if (isNeedChangeStatusBarMode()) {
            updateStatusBarMode(isStatusBarLightMode())
        }
    }

    override fun onResume() {
        super.onResume()
        if (lifecycle.currentState == Lifecycle.State.STARTED) {
            onLazyResume()
            if (mIsFirstLayInit) {
                mIsFirstLayInit = false
                view?.post {
                    onLazyInit()
                }
            }
        }
    }

    /**
     * 原本懒加载当[isIndexFragment] = True时是在界面动画结束后
     * 但是当为 BaseFragmentActivity 的 DefaultFirstFragment 时，[isIndexFragment] = True , 但是并不会走 onEnterAnimationEnd()
     * 所以现在全部以界面可见时为懒加载时机
     */
    override fun onEnterAnimationEnd(animation: Animator?) {
        super.onEnterAnimationEnd(animation)
    }

    /**
     * 显示加载框
     * @param msg String? 提示语
     * @remark 这了提供了默认的加载效果，如果需要更改，重写此方法以及[hideLoadingDialog]
     */
    override fun showLoadingDialog(msg: String?) {
        context?.showLoadingDialog(msg)
    }

    /**
     * 隐藏加载框
     */
    override fun hideLoadingDialog() {
       hideLoadingDialogExt()
    }

    /**
     * 向外提供的关闭方法
     */
    open fun finish() {
        // 结束前关闭Dialog，避免内存泄漏
        hideLoadingDialog()
        hideProgressDialog()
        onBackPressed()
    }

}